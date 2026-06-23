package com.wanted.crud.enrollment.controller;

import com.wanted.crud.course.model.dto.CourseDTO;
import com.wanted.crud.enrollment.model.dto.EnrollmentDTO;
import com.wanted.crud.enrollment.model.dto.EnrollmentStudentDTO;
import com.wanted.crud.enrollment.model.service.EnrollmentService;
import com.wanted.crud.enrollment.view.EnrollmentOutputView;

import java.util.List;

public class EnrollmentController {
    private final EnrollmentService service;

    public EnrollmentController(EnrollmentService service) {this.service = service;}
    public boolean createEnrollment(Long studentId, Long courseId) {



        /* comment.
         *   타이틀과 설명은 논리적으로 묶여야 하는 데이터이다.
         *   authorId 는 나중에 로그인을 한 유저 객체에서 추출해서 넣어주어야 한다.*/
        EnrollmentDTO newEnrollment = new EnrollmentDTO(studentId, courseId);
        return service.saveEnrollment(newEnrollment);
    }

    // 수강생 강좌 목록 출력
    public void studentCoursePage(Long studentId) {
        List<EnrollmentDTO> list = service.studentCoursePage(studentId);
        EnrollmentOutputView.printStudentCourses(list);

    }
    
    // 수강생 강좌 중복 여부
    public boolean isStudyingCoruse(Long studentId, Long courseId) {
        return service.isStudyingCourse(studentId, courseId);
    }

    public boolean updateEnrollmentProgress(Long studentId, Long courseId){
        return service.updateEnrollmentProgress(studentId, courseId);
    }




}
