package com.wanted.crud.user.controller;

import com.wanted.crud.enrollment.model.dto.EnrollmentStudentDTO;
import com.wanted.crud.user.model.dto.UserDTO;
import com.wanted.crud.user.model.dto.StudentDTO;
import com.wanted.crud.user.model.dto.InstructorDTO;

import com.wanted.crud.user.model.service.UserService;

import java.sql.SQLException;
import java.util.List;

public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // ===== User =====
    public List<UserDTO> selectAllUsers() {
        return service.selectAllUsers();
    }

    public UserDTO login(String id, String password) {
        return service.login(id, password);
    }

    public Long createUser(String id, String password, String name, String phoneNumber, String role) {

        UserDTO newUser = new UserDTO(null, id, password, name, phoneNumber, null, role, null, true);
        return service.saveUser(newUser);
    }

    // ===== Student =====
    public List<StudentDTO> selectAllStudents() {
        return service.selectAllStudents();
    }

    // 회원탈퇴
    public boolean dropUser(String id, String password) {

        return service.dropUser(id, password);
    }



    // 비번 찾기 메서드
    public String findPassword(String userid, String phoneNumber) {
        return service.findPassword(userid, phoneNumber);
    }

    public String findId(String name, String phoneNumber) {
        return service.findId(name, phoneNumber);
    }

    // ===== Instructor =====
    public List<InstructorDTO> selectAllInstructors() {
        return service.selectAllInstructors();
    }

    public Long instructorFindId(Long userno) {
        return service.findInstructorId(userno);
    }
    public Long studentFindId(Long userno) {
        return service.findStudentId(userno);
    }
    public Long getAmount(Long userNo) {
        return service.getAmount(userNo);
    }


    public boolean updateStudent(UserDTO userDTO) {
        return service.updateStudent(userDTO);
    }
    public boolean updateInstructor(UserDTO userDTO) {
        return service.updateInstructor(userDTO);
    }

    public UserDTO findSelectUserNo(Long userNo) {
        return service.findSelectUserNo(userNo);
    }

    public UserDTO findSelectUserRole(String userRole) {
        return service.findSelectUserRole(userRole);
    }


    // 관리자의 강좌별 수강생 조회
    public  List<EnrollmentStudentDTO>  viewStudentBycourseId() {

        return service.viewStudentBycourseId();
    }

    // 관리자의 강사 이름 검색으로 조회
    public List<UserDTO> findInstructorByName(String name) {
        return service.findInstructorByName(name);

    }

    // 관리자의 수강생 전체 조회
    public List<UserDTO> findAllStudents() {
        return service.findAllStudents();
    }

    // 관리자의 수강생 정보 수정
    public boolean updateStudentinfo(Long userNo, String newName, boolean status) {

        return service.updateStudentinfo(userNo, newName, status);
    }


    // 관리자의 비활성화 수강생 조회
    public List<UserDTO> findInactiveStudents() {
        {
            return service.findInactiveStudents();
        }
    }

    // 관리자의 수강생 삭제
    public boolean deleteStudent(Long userNo) {
        return service.deleteStudent(userNo);
    }

    // 관리자의 전체 강사 조회
    public List<UserDTO> findAllInstructor() {
        return service.findAllInstructor();
    }

    // 관리자의 강사 정보 수정

     public boolean updateInstructorinfo(Long userNo, String newName, boolean status) {

        return service.updateInstructorinfo(userNo, newName, status);
    }
    // 관리자의 비활성화 강사 조회

    public List<UserDTO> findInactiveInstructors() {
        {
            return service.findInactiveInstructors();
        }
    }

    // 관리자의 비활성화 강사 삭제
    public boolean deleteInstructor(Long userNo) {
        {
            return service.deleteInstructor(userNo);
        }
    }


    //유저 금액 업데이트
    public boolean updateAmount(Long userNo, Long amount) {
        return service.updateAmount(userNo, amount);
    }


}