package com.wanted.cleanarchitecture.learning.application.port;

/*
 * TODO: 학생 실습용 스켈레톤
 *
 * Learning 컨텍스트는 학습 완료를 처리하기 전에
 * "이 사용자가 해당 강의를 활성 수강 중인가?"를 확인해야 한다.
 *
 * 현재 LearningAccessPolicy는 enrollment의 Repository를 직접 사용한다.
 * 수업 실습에서는 CourseCatalogPort 예제를 참고해 이 Port를 사용하도록 바꿔본다.
 *
 * 힌트:
 * - learning은 Enrollment Aggregate 전체가 필요하지 않다.
 * - 필요한 것은 활성 수강 여부라는 boolean 결과다.
 * - 구현 Adapter는 enrollment infrastructure 쪽 조회를 감싸면 된다.
 */
public interface EnrollmentAccessPort {

    boolean hasActiveEnrollment(Long userId, Long courseId);
}
