package com.wanted.cleanarchitecture.learning.infrastructure.adapter;

import com.wanted.cleanarchitecture.enrollment.domain.repository.EnrollmentRepository;
import com.wanted.cleanarchitecture.learning.application.port.EnrollmentAccessPort;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentAccessAdapter implements EnrollmentAccessPort {

    // 1. 수강 신청(Enrollment) 정보를 관리하는 Repository를 가져옵니다.
    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentAccessAdapter(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public boolean hasActiveEnrollment(Long userId, Long courseId) {
        // 2. 무조건 false가 아니라, 실제로 수강 중인지 DB에 물어본 결과를 반환합니다.
        return enrollmentRepository.existsActiveEnrollment(userId, courseId);
    }
}