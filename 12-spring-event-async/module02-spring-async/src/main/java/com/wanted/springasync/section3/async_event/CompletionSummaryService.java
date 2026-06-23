package com.wanted.springasync.section3.async_event;

import com.wanted.springasync.common.support.SleepUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class CompletionSummaryService {

    /* comment.
    *   비동기 메서드의 반환형 차이
    *   1. void : 비동기 메서드 호출하는 곳은 비동기 완료 결과를 받을 수 없다.
    *   2. CompletableFuture : 호출자는 thenAccept, join(), get() 등으로
    *   비동기 메서드 완료 결과를 이어서 다룰 수 있게 된다.
    * */
    @Async  // 어떤 비동기 메소드를 사용할 것인지 작성하지 않음.
    // CompletableFuture 사용하는 이유 : 언제 들어올지 모르는 미래의 값을 대비하기 위해.
    public CompletableFuture<String> createSummaryAsync(Long enrollmentId) {

        log.info("[section03]🚨비동기🚨 수강 완료 시 진행되는 이벤트 작업 시작! 작업 중인 Thread = {}", Thread.currentThread().getName());

        SleepUtils.sleep(3000L);

        String summary = "enrollmentId = " + enrollmentId + "수강 완료 요약본 생성됨!!!";

        log.info("[section03]🚨비동기🚨 수강 완료 시 진행되는 이벤트 작업 종료! 작업 중인 Thread = {}", Thread.currentThread().getName());

        return CompletableFuture.completedFuture(summary);
    }
}
