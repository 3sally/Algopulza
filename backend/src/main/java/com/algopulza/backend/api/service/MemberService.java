package com.algopulza.backend.api.service;

import com.algopulza.backend.api.request.member.ModifyMemberProfileImageReq;
import com.algopulza.backend.api.request.member.ModifyMemberReq;
import com.algopulza.backend.api.response.MemberRes;
import com.algopulza.backend.db.entity.Member;

public interface MemberService {

    MemberRes getMember(Long memberId);

    void modifyMemberProfileImage(ModifyMemberProfileImageReq modifyMemberProfileImageReq);

    void addMember(String solvedacToken);

    void modifyMember(ModifyMemberReq modifyMemberReq);
}
