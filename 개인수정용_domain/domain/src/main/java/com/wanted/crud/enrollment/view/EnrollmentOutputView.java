package com.wanted.crud.enrollment.view;

import com.wanted.crud.enrollment.model.dto.EnrollmentDTO;

import java.util.List;

public class EnrollmentOutputView {
    public static void printStudentCourses(List<EnrollmentDTO> list) {

        System.out.println("\n📖 [ 나의 수강 강좌 목록 ]");

        if (list == null || list.isEmpty()) {
            System.out.println("수강 중인 강좌가 없습니다.");
            return;
        }

        for(EnrollmentDTO dto : list){
            System.out.println(dto);
        }
    }
}