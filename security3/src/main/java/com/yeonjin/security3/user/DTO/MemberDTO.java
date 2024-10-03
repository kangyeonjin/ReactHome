package com.yeonjin.security3.user.DTO;

import com.yeonjin.security3.user.Entity.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberDTO {
    private int memberNo;

    private String memberId;

    private String memberPass;

    private String memberName;

    private String memberEmail;

    private Role role;
}
