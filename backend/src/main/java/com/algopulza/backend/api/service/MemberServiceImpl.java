package com.algopulza.backend.api.service;

import com.algopulza.backend.api.request.member.ModifyMemberProfileImageReq;
import com.algopulza.backend.api.request.member.ModifyMemberReq;
import com.algopulza.backend.api.response.MemberRes;
import com.algopulza.backend.common.exception.NotFoundException;
import com.algopulza.backend.common.exception.handler.ErrorCode;
import com.algopulza.backend.db.entity.*;
import com.algopulza.backend.db.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service("memberService")
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final LoginLogRepository loginLogRepository;
    private final TierRepository tierRepository;
    private final OrganizationRepository organizationRepository;
    private final MemberHasOrganizationRepository memberHasOrganizationRepository;
    private final ProblemRepository problemRepository;
    private final SolvingLogRepository solvingLogRepository;
    private final S3Service s3Service;

    @Override
    public MemberRes getMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_MEMBER));
        MemberRes memberRes = MemberRes.builder()
                                       .memberId(member.getId())
                                       .memberName(member.getName())
                                       .profileImage(member.getProfileImage())
                                       .email(member.getEmail())
                                       .level(member.getTier().getLevel())
                                       .tierName(member.getTier().getName())
                                       .solveCount(member.getSolveCount())
                                       .daysCount(member.getDaysCount())
                                       .build();
        return memberRes;
    }

    @Override
    public void modifyMember(ModifyMemberReq modifyMemberReq) {
        //organization 등록
        addOrganization(0,modifyMemberReq.getOrganizationName(),modifyMemberReq.getOrganizationType());

        //memberHasOrganization 등록
        Optional<Member> member = memberRepository.findById(modifyMemberReq.getMemberId());
        addMemberHasOrganization(member.get().getName(), modifyMemberReq.getOrganizationName());
    }

    @Override
    public void modifyMemberProfileImage(ModifyMemberProfileImageReq modifyMemberProfileImageReq) {
        Member member = memberRepository.findById(modifyMemberProfileImageReq.getMemberId()).orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_MEMBER));

        member.setProfileImage(s3Service.uploadToMember(modifyMemberProfileImageReq.getProfileImage()));
        memberRepository.save(member);
    }

    @Override
    public void addMember(String solvedacToken) {

        //solvedac API 활용해서 member 정보 받아오기
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Cookie", "solvedacToken=" + solvedacToken);

        HttpEntity<String> entity = new HttpEntity<String>("", headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> memberInfo
                = restTemplate.exchange("https://solved.ac/api/v3/account/verify_credentials", HttpMethod.GET, entity, String.class);


        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(memberInfo.getBody());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println(jsonNode);

        String name = jsonNode.get("user").get("handle").toString().substring(1, jsonNode.get("user").get("handle").toString().length() - 1);

        //회원 검색
        Optional<Member> member = Optional.ofNullable(memberRepository.findByName(name));

        //이미 등록된 회원이면 solvedac token만 업데이트, 아니면 새 회원으로 등록
        JsonNode finalJsonNode = jsonNode;
        member.ifPresentOrElse(selectMember -> {
            System.out.println("member already exist!!");
            selectMember.setSolvedacToken(solvedacToken);
            memberRepository.save(selectMember);
        }, ()->{
            System.out.println("new member!!");

            String profileImage = finalJsonNode.get("user").get("profileImageUrl").toString();
            String email = finalJsonNode.get("user").get("email").toString();
            Long tier = Long.parseLong(finalJsonNode.get("user").get("tier").toString());

            Tier getTier = tierRepository.findByLevel(tier);

            Member newMember = new Member();
            newMember.setTier(getTier);
            newMember.setName(name);
            newMember.setProfileImage(profileImage.substring(1,profileImage.length()-1));
            newMember.setSolveCount(Integer.parseInt(finalJsonNode.get("user").get("solvedCount").toString()));
            newMember.setEmail(email.substring(1,email.length()-1));
            newMember.setDaysCount(0);
            newMember.setSolvedacToken(solvedacToken);
            memberRepository.save(newMember);
        });

        int organizationSize = jsonNode.get("user").get("organizations").size();
        for (int i = 0; i < organizationSize; i++) {
            //organization 등록
            int organizationId = Integer.parseInt(jsonNode.get("user").get("organizations").get(i).get("organizationId").toString());
            String organizationName = jsonNode.get("user").get("organizations").get(i).get("name").toString();
            String organizationType = jsonNode.get("user").get("organizations").get(i).get("type").toString();
            addOrganization(organizationId, organizationName, organizationType);
            //memberHasOrganization 등록
            addMemberHasOrganization(name, organizationName);
        }

        int solvedSize = jsonNode.get("solved").size();
        for (int i = 0; i < solvedSize; i++) {
            //solving_log 등록
            int problemId = Integer.parseInt(jsonNode.get("solved").get(i).get("id").toString());
            String status = jsonNode.get("solved").get(i).get("status").toString();
            addSolvingLog(name,problemId, status);
        }

        //login_log 등록
        addLoginlog(name);

    }

    private void addSolvingLog(String name, int problemId, String status) {
        Problem problem = problemRepository.findByBojId(problemId);
        Optional<Member> member = Optional.ofNullable(memberRepository.findByName(name));
        member.ifPresent(selectMember->{
            SolvingLog solvingLog = new SolvingLog();
            solvingLog.setMember(selectMember);
            solvingLog.setProblem(problem);
            solvingLog.setStatus(status);
            solvingLogRepository.save(solvingLog);
        });
    }

    //TODO 이미 추가된 그룹이면 memberHasOraganization에 추가 등록 안되게 로직 짜야함
    private void addMemberHasOrganization(String name, String organizationName) {
        Organization organization = organizationRepository.findByName(organizationName);
        Optional<Member> member = Optional.ofNullable(memberRepository.findByName(name));
        member.ifPresent(selectMember->{
            MemberHasOrganization memberHasOrganization = new MemberHasOrganization();
            memberHasOrganization.setMember(selectMember);
            memberHasOrganization.setOrganization(organization);
            memberHasOrganizationRepository.save(memberHasOrganization);
        });
    }

    private void addOrganization(int organizationId, String organizationName, String organizationType) {
        int typeFlag;
        switch (organizationType){
            case "community" :
                typeFlag = 1;
                break;
            case "university" :
                typeFlag = 2;
                break;
            case "company" :
                typeFlag = 3;
                break;
            case "high_school" :
                typeFlag = 4;
                break;
            default:
                typeFlag = 0;
                break;
        }

       Organization organization =  organizationRepository.findByName(organizationName);
       //이미 존재하는 organization이면 pass, 아니면 새로 등록
        if (organization!=null) {
            System.out.println("organization already exist!!");
        }else{
            System.out.println("new organization");
            Organization newOrganiation = new Organization();
            newOrganiation.setBojId(organizationId);
            newOrganiation.setName(organizationName);
            newOrganiation.setTypeFlag(typeFlag);
            organizationRepository.save(newOrganiation);
        }
    }

    private void addLoginlog(String name) {
        Optional<Member> member = Optional.ofNullable(memberRepository.findByName(name));
        member.ifPresent(selectMember->{
            LoginLog loginLog = new LoginLog();
            loginLog.setMember(selectMember);
            loginLogRepository.save(loginLog);
        });
    }
}
