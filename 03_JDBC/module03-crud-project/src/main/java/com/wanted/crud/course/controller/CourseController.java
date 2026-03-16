package com.wanted.crud.course.controller;

import com.wanted.crud.course.model.dto.CourseDTO;
import com.wanted.crud.course.model.dto.CourseSectionDTO;
import com.wanted.crud.course.model.dto.SectionDTO;
import com.wanted.crud.course.model.service.CourseService;

import java.util.List;

public class CourseController {

    /* comment.
    *   * Controller 계층의 책임
    *   - Controller 는 View 와 Service 사이를 연결하는 커멘드 센터
    *   - View 가 사용자에게 입력을 받고, 그 입력을 Controller에게 전달하면
    *     Controller 는 적절한 Service 계층의 메서드를 호출한다.
    *   .
    *   * Controller가 해야 할 일
    *   - 1. View 에서 받은 요청을 처리하는 메서드
    *   - 2. Service 메서드 호출 코드
    *   - 3. 필요하면 DTO/객체를 조립하는 코드
    *   .
    *   * Controller가 해면 안 되는 일
    *   - 1. Scanner 입력처리 (View에서 하고 있으니까)
    *   - 2. 출력처리
    *   - 3. SQL 작성
    *   - 4. commit / rollback 작업
    * */

    private final CourseService service;

    public CourseController(CourseService service) {
        this.service = service;
    }

    public boolean updateCourse(long id, String title, String description) {
        return true;
    }

    public List<CourseDTO> findAllCourses() {
        return service.findAllCourses();
    }

    /**
     * 사용자가 입력한 데이터를 바탕으로 강좌를 삽입
     * @param title 사용자가 입력한 강좌의 제목
     * @param description 사용자가 입력한 강좌의 설명
     * */
    public Long createCourse(String title, String description) {

        /* comment. 타이틀과 설명은 논리적으로 묶여야 하는 데이터이다. (제목 + 설명)
        *   authorID 는 나중에 로그인을 한 유저 객체에서 추출해서 넣어주어야 한다.
        * */
        // 객체조립할 거임. 묶여야 함 = class 자료형 사용. / 강좌 관련 = courseDTO.
        // courseId는 자동 증가, status도 default 값이 draft라 null해도 됨.
        CourseDTO newCourse = new CourseDTO(null, 1L, title, description, "draft");    // courseId, authorId, title, description, status
        return service.saveCourse(newCourse);
    }

    public boolean deleteCourseById(long id) {
        // 문자열로 가장 쉽게 바꾸는 방법?
        // + ""
        // 문자 -> 숫자로 가장 쉽게 바꾸는 방법?
        // + 0
        // int -> boolean으로 변경
        // > 0
        return service.deleteCourse(id) > 0;        // controller가 원하는 리턴값은 boolean 형이기에 '>0'을 넣어서 int 값을 boolean으로.
    }

    public CourseDTO findCourseById(long id) {

        return service.findById(id);

    }

    public CourseSectionDTO findJoin(long courseId) {
        return service.findCourseWithSections(courseId);
    }

    /**
     * 트랜잭션 테스트 전용 메소드(강의와 섹션 동시 삽입)
     * */
    public boolean createCourseWithDefaultSection() {
        CourseDTO newCourse =  new CourseDTO(null, 1L, "Java Transaction Master",
                                             "트렌잭션을 활용한 강의 등록", "draft");
        SectionDTO newSection = new SectionDTO(null, null, "Chapter 1. 트랜젝션의 이해", 1);

        return service.createCourseWithDefaultSection(newCourse, newSection);
    }
}
