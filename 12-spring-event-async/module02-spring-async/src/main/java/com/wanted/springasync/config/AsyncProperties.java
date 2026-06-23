package com.wanted.springasync.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.async")  // properties.yaml 에 작성 app.async
public record AsyncProperties(
        // 필드
        /* comment.
        *   yml 에 작성한 async 관련 값을 변수처리하여 활용하기 위함.
        *   케밥케이스(꼬치에 꽂힌 모양) 단어-단어 / 카멜케이스 단어딴어
        * */
        // 변수명은 이미 정해져 있음 (yml 파일에)
        int corePoolSize,
        int maxPoolSize,
        int queueCapacity,
        String threadNamePreFix
) {



}
