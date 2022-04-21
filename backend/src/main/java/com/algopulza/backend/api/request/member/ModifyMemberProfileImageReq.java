package com.algopulza.backend.api.request.member;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@ApiModel("ModifyMemberProfileImageReq")
@Data
public class ModifyMemberProfileImageReq {

    @ApiModelProperty(value = "회원 아이디", required = true)
    private Long memberId;

    @ApiModelProperty(value = "변경할 프로필 사진", required = true)
    private MultipartFile profileImage;

}
