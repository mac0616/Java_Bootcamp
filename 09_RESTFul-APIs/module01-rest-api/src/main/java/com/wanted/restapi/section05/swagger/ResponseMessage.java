package com.wanted.restapi.section05.swagger;

// 해당 클래스는 응답 템플릿

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseMessage {

    @Schema(description = "HTTP 응답 상태코드", example = "200")
    private int httpStatus;
    @Schema(description = "HTTP 응답 메세지", example = "성공")
    private String message;
    // 문자열을 표기하는 "" 내부에 ""를 사용하게 되면 컴파일 에러가 발생한다.
    // 이럴 때는 \ 역슬래시(이스케이프 문자) 를 사용해서 단순 문자열값으로 판단하게 해야 한다.
    @Schema(description = "HTTP 응답 데이터", example = "{ \"id\" : 1 , \"name\" : \"홍길동\"}")
    private Map<String, Object> result;

}
