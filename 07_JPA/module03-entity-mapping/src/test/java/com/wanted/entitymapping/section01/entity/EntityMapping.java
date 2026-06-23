package com.wanted.entitymapping.section01.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Member;
import java.time.LocalDateTime;
import java.util.stream.Stream;

@SpringBootTest
public class EntityMapping {

    @Autowired
    private MemberService memberService;        // 필드 주입.   / 클래스는 main에 생성하기.

    private static Stream<Arguments> getMember() {
        return Stream.of(
                Arguments.of(
                        1,
                        "user01",
                        "pass01",
                        "너구리",
                        "010-5555-5555",
                        "수원시 장안구",
                        LocalDateTime.now(),
                        "ROLE_MEMBER",
                        "Y"
                ),
                Arguments.of(           // Arguments = 전달인자 / 아래에 값 하나씩 넣음.
                        2,
                        "user02",
                        "pass02",
                        "윤라프♥️",
                        "010-0126-1004",
                        "거북시 라프동",
                        LocalDateTime.now(),
                        "ROLE_MEMBER",
                        "Y"
                )
        );
    }

    @ParameterizedTest
    @DisplayName("테이블 DDL 테스트")
    @MethodSource("getMember")
    void testCreateTable(int memberNo, String memberId, String memberPwd,
                         String memberName, String phone, String address,
                         LocalDateTime enrollDate, MemberRole memberRole, String status) {

        // 사용자의 입력값을 담을 DTO
        MemberRegistDTO newMember = new MemberRegistDTO(
                memberId, memberPwd, memberName, phone,
                address, enrollDate, memberRole, status
        );

        // 메소드 검증 시 해당 메소드가 Throw 예외를 발생 시키는지 확인한다.
        // 예외 발생하지 않으면 테스트 통과, 그렇지 않으면 불통과
        Assertions.assertDoesNotThrow(
//                전달할 값이 있다면 (33) -> memberService.registMember(newMember) 이렇게 가능
                () -> memberService.registMember(newMember)
        );
    }


}
