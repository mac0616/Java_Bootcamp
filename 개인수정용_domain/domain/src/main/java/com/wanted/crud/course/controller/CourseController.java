package com.wanted.crud.course.controller;

import com.wanted.crud.course.model.dto.CourseMyStudentDTO;
import com.wanted.crud.course.model.dto.CourseReviewDTO;
import com.wanted.crud.course.model.dto.SectionDTO;
import com.wanted.crud.course.model.dto.CourseDTO;
import com.wanted.crud.course.model.service.CourseService;
import com.wanted.crud.enrollment.model.dto.EnrollmentStudentDTO;

import java.util.List;

public class CourseController {

    private final CourseService service;

    public List<CourseDTO> selectAllCourses() {
        return service.findAllCourses();
    }

    public CourseController(CourseService service) {
        this.service = service;
    }

    public List<CourseDTO> selectCourse(long id){
        return service.findMyCourse(id);
    }

    public Long createCourse(String title, String description, long price, long id) {
        CourseDTO newCourse = new CourseDTO(null, title, description, price, "1", null, id);
        return service.saveCourse(newCourse);
    }

    // title, video_url, material_url, created_at, course_id
    public Long createSection(String title, String video_url, String material_url, long id){
        SectionDTO newSection = new SectionDTO(null, title, video_url, material_url, null ,id);
        return service.saveSection(newSection);
    }

    public boolean deleteCourseById(Long courseId, Long instructorId) {
        return service.deleteCourseById(courseId, instructorId);
    }

    // 특정 강좌에 속한 강의(Section) 목록 조회
    public List<SectionDTO> selectSectionsByCourseId(Long courseId) {
        return service.findSectionsByCourseId(courseId);
    }

    public List<Long> findCoursesId(Long instructorId) {
        return service.findCoursesId(instructorId);
    }

    public boolean updateCourse(Long courseId, String title, String description, Long price, Long instructorId) {
        CourseDTO updateCourse = new CourseDTO(courseId, title, description, price, null, null, instructorId);
        return service.updateCourse(updateCourse);
    }

    // 내 강의 수정
    public boolean updateSection(Long sectionId, String title, String videoUrl, String materialUrl, Long instructorId) {
        SectionDTO section = new SectionDTO(sectionId, title, videoUrl, materialUrl, null, null);
        return service.updateSection(section, instructorId);
    }

    // 관리자가 강사 강의 조회
    public List<CourseDTO> getCoursesByInstructor(Long targetInstructorId) {
        return service.findCoursesByInstructorId(targetInstructorId);
    }

    // 관리자가 강사 강좌 수정
    public boolean updateCourseByAdmin(Long courseId, String title, String description, Long price, String status) {
        CourseDTO updateCourse = new CourseDTO(courseId, title, description, price, status, null, null);
        return service.updateCourseByAdmin(updateCourse);
    }

    public Long getPirce(Long courseId) {
        return service.getPrice(courseId);
    }


    // 내 강좌를 수강중인 학생 전체 조회
    public List<CourseMyStudentDTO> selectFindMyStudent(Long instructorId){
        return service.selectFindMyStudent(instructorId);
    }


    // 관리자가 강좌 삭제
    public boolean deleteCourseByAdmin(Long courseId) {
        return service.removeCourseByAdmin(courseId);
    }


    // 리뷰
    public List<EnrollmentStudentDTO>getCompletedCourses(Long studentId){
        return service.completedCourses(studentId);
    }

    public boolean writeReview(Long studentId, Long courseId, Long rating){
        return service.writeReview(studentId, courseId, rating);
    }

    public List<CourseReviewDTO> getCourseReview() {
        return service.getCourseReview();
    }

    public boolean isCourseExists(Long courseId) {
        return service.isCourseExists(courseId);
    }
}
