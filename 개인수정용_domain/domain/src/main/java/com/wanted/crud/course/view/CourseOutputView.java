package com.wanted.crud.course.view;

import com.wanted.crud.course.model.dto.CourseDTO;
import com.wanted.crud.course.model.dto.CourseMyStudentDTO;

import java.util.List;

public class CourseOutputView {

    public void printMessage(String s) {
        System.out.println("💬 " + s);
    }

    public void printCourses2(List<CourseDTO> courseList) {
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("🔎 [ 모든 강좌 검색 결과 ]");

        if (courseList == null || courseList.isEmpty()) {
            System.out.println("🚨 [알림] 현재 등록된 강좌가 하나도 없습니다.");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            return;
        }

        for (CourseDTO courseDTO : courseList) {
            System.out.println(courseDTO);
        }
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }

    public void printCourses(List<CourseDTO> findMyCourse) {
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("📂 [ 강좌 목록 리포트 ]");

        if (findMyCourse == null || findMyCourse.isEmpty()) {
            System.out.println("🚨 [알림] 조회된 강좌 데이터가 없습니다.");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            return;
        }

        for (CourseDTO courseDTO : findMyCourse) {
            System.out.println(courseDTO);
        }
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }




    public void printSuccess(String message) {
        System.out.println("\n✨ [SUCCESS] ✅ " + message);
    }

    public void printError(String message) {
        System.out.println("\n🚨 [ERROR] ❌ " + message);
    }

    public void printMyStudent(List<CourseMyStudentDTO> findStudent) {
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        if (findStudent == null || findStudent.isEmpty()) {
            System.out.println("🚨 [알림] 조회된 학생 데이터가 없습니다.");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            return;
        }

        for (CourseMyStudentDTO courseMyStudentDTO : findStudent) {
            System.out.println(courseMyStudentDTO);
        }
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

    }
}