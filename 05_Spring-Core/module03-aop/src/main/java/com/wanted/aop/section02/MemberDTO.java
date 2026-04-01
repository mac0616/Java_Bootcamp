package com.wanted.aop.section02;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
//@Data // 위 5줄과 같음. 권장하지 않는 방법.
public class MemberDTO {
    private String email;
    private String password;
    private String name;
    private String phone;
    private String role;
}
