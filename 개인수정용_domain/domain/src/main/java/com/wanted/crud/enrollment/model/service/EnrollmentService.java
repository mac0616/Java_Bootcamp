package com.wanted.crud.enrollment.model.service;

import com.wanted.crud.course.model.dto.CourseDTO;
import com.wanted.crud.enrollment.model.dao.EnrollmentDAO;
import com.wanted.crud.enrollment.model.dto.EnrollmentDTO;
import com.wanted.crud.enrollment.model.dto.EnrollmentStudentDTO;
import com.wanted.crud.payment.model.dao.PaymentDAO;
import com.wanted.crud.user.model.dto.UserDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class EnrollmentService {
    private final EnrollmentDAO enrollmentDAO;
    private final Connection connection;

    public EnrollmentService(Connection connection) {
        this.connection = connection;
        this.enrollmentDAO = new EnrollmentDAO(connection);
    }

    //결제 시 수강내역저장
    public boolean saveEnrollment(EnrollmentDTO newEnrollment) {
        try {
            return enrollmentDAO.save(newEnrollment);
        } catch (SQLException e) {
            throw new RuntimeException("수강 신청 중 Error 발생!!! 🚨" + e);
        }
    }

    // 수강생 강좌 목록 출력
    public List<EnrollmentDTO> studentCoursePage(Long studentId) {
        try {
            return enrollmentDAO.studentCoursePage(studentId);
        } catch (SQLException e) {
            throw new RuntimeException("강좌 목록 조회 중 Error 발생!! ", e);
        }

    }

    //결제 시 수강중인 강좌가 있는지 확인
    public boolean isStudyingCourse(Long studentId, Long courseId) {
        try {
            // 해당 학생의 수강 강좌 리스트를 돌면서
            for(long courseId_temp : enrollmentDAO.studyingCourse(studentId)) {
                // 데이터가 하나라도 존재해서 이 루프에 진입했다면 수강 중인 것!
                if(courseId_temp == courseId) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("수강중인 강의 확인 중 오류 발생!!", e);
        }
        return false; // 리스트가 비어있어서 루프를 안 돌았다면 false
    }


    public boolean updateEnrollmentProgress(Long studentId, Long courseId){
        try {
            int result = enrollmentDAO.updateProgress(studentId, courseId);
            return result > 0;

        } catch (SQLException e){
            throw new RuntimeException("🚨 수강 진척도 업데이트 중 Error 발생!!", e);
        }
    }




}


