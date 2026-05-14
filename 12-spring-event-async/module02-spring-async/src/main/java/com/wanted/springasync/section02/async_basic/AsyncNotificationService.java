package com.wanted.springasync.section02.async_basic;

import com.wanted.springasync.common.support.SleepUtils;
import com.wanted.springasync.domain.course.Enrollment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AsyncNotificationService {

    // AsyncConfig 에서 만들어둔 Bean 활용
    @Async("classTaskExecutor")
    public void sendCompletionEmail(Enrollment enrollment) {

        log.info("[section02] 비동기 수료 메일 발송 시작! enrollmentId = {}", enrollment.getId());

        SleepUtils.sleep(3000L);

        log.info("[section02] 비동기 수료 메일 발송 종료! user = {}", enrollment.getUser());

    }
}
