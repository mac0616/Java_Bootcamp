package com.wanted.crud.user.model.service;

import com.wanted.crud.enrollment.model.dao.EnrollmentDAO;
import com.wanted.crud.enrollment.model.dto.EnrollmentStudentDTO;
import com.wanted.crud.user.model.dao.UserDAO;
import com.wanted.crud.user.model.dao.StudentDAO;
import com.wanted.crud.user.model.dao.InstructorDAO;

import com.wanted.crud.user.model.dto.UserDTO;
import com.wanted.crud.user.model.dto.StudentDTO;
import com.wanted.crud.user.model.dto.InstructorDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserService {

    private final UserDAO userDAO;
    private final StudentDAO studentDAO;
    private final InstructorDAO instructorDAO;
    private final Connection connection;
    private final EnrollmentDAO enrollmentDAO;

    public UserService(Connection connection) {
        this.connection = connection;
        this.userDAO = new UserDAO(connection);
        this.studentDAO = new StudentDAO(connection);
        this.instructorDAO = new InstructorDAO(connection);
        this.enrollmentDAO = new EnrollmentDAO(connection);
    }

    // ===== User =====
    public List<UserDTO> selectAllUsers() {
        try {
            return userDAO.selectAll();
        } catch (SQLException e) {
            throw new RuntimeException("유저 전체 조회 중 Error 발생!! /UserService2"+e);
        }
    }



    public String findId(String name, String phone_number) {
        try {
            return userDAO.findId(name, phone_number);
        } catch (SQLException e) {
            throw new RuntimeException("유저 아이디를 찾는 중 Error 발생!!" +e);
        }
    }

    public String findPassword(String userid, String userphonenumber ) {
        try {
            return userDAO.findPassword(userid, userphonenumber);
        } catch (SQLException e) {
            throw new RuntimeException("비밀번호 찾는 중 에러 발생 !!" + e);
        }
    }

    public UserDTO login(String id, String password) {
        try {
            return userDAO.login(id, password);
        } catch (SQLException e) {
            throw new RuntimeException("DB 오류 발생", e); // ✔ 이렇게
        }
    }

    public Long saveUser(UserDTO newUser) {
        try {
            return userDAO.save(newUser);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("회원가입 중 Error 발생!!! 🚨" + e);
        }
    }

    // ===== Student =====
    public List<StudentDTO> selectAllStudents() {
        try {
            return studentDAO.selectAll();
        } catch (SQLException e) {
            throw new RuntimeException("학생 전체 조회 중 Error 발생!! /UserService2" + e);
        }
    }

    public UserDTO findSelectUserNo(Long userNo) {
        try {
            return userDAO.findSelectUserNo(userNo);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public UserDTO findSelectUserRole(String userRole) {
        try {
            return userDAO.findSelectUserRole(userRole);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public StudentDTO findPassword(long id) throws SQLException {
        return studentDAO.findById(id);
    }



    // 회원 탈퇴
    public boolean dropUser(String id, String password) {
        try {
            // DAO에서 boolean 반환
            return userDAO.dropUser(id, password);
        } catch (SQLException e) {
            throw new RuntimeException("회원 탈퇴 중 오류 발생", e);
        }
    }

    //학생 업데이트
    public boolean updateStudent(UserDTO userDTO) {
        try {
            return studentDAO.updateStudent(userDTO) > 0;
        } catch (SQLException e) {
            throw new RuntimeException("학생정보 업데이트 중 오류" + e);
        }
    }

    //강사 업데이트
    public boolean updateInstructor(UserDTO userDTO) {
        try {
            return instructorDAO.updateInstructor(userDTO) > 0;
        } catch (SQLException e) {
            throw new RuntimeException("강사 정보 업데이트중 오류" + e);
        }
    }


    // ===== Instructor =====
    public List<InstructorDTO> selectAllInstructors() {
        try {
            return instructorDAO.selectAll();
        } catch (SQLException e) {
            throw new RuntimeException("강사 전체 조회 중 Error 발생!! /UserService2", e);
        }
    }

    // 강사 번호 리턴
    public Long findInstructorId(Long userNo) {
        try {
            if(instructorDAO.findId(userNo) != null) {
                return instructorDAO.findId(userNo);
            } else System.out.println("조회된 강사번호가 없음");
        } catch (SQLException e) {
            throw new RuntimeException("유저번호를 통한 강사번호 조회 중 오류발생!! +", e);
        }
        return null;
    }


    // 학생 번호 리턴
    public Long findStudentId(Long userNo) {
        try {
            if(studentDAO.findId(userNo) != null) {
                return studentDAO.findId(userNo);
            } else System.out.println("조회된 수강생 번호가 없음");
        } catch (SQLException e) {
            throw new RuntimeException("유저번호를 통한 수강생 번호 조회 중 오류발생!! +", e);
        }
        return null;
    }

    public Long getAmount(Long userNo) {
        try {
            if(userDAO.getAmount(userNo) != null) {
                return userDAO.getAmount(userNo);
            } else System.out.println("잔액이 확인되지않음");
        } catch (SQLException e) {
            throw new RuntimeException("유저번호를 통한 수강 잔액확인불가+", e);
        }
        return null;
    }

    // ===== Admin =====
    // 관리자의 강좌별 수강생 조회
    public List<EnrollmentStudentDTO> viewStudentBycourseId() {
        try {
            return enrollmentDAO.selectStudentByCourseid();
        } catch (SQLException e) {
            throw new RuntimeException("수강생 전체 조회 중 Error 발생!! ", e);
        }
    }


    // 관리자의 강사 이름으로 조회
    public List<UserDTO> findInstructorByName(String name) {
        try {
            return userDAO.findInstructorByName(name);
        } catch (SQLException e) {
            throw new RuntimeException("강사 이름 조회 중 에러 발생 !!" + e);
        }
    }



    public List<UserDTO> findAllStudents() {
        try {
            return userDAO.findAllStudents();
        } catch (SQLException e) {
            throw new RuntimeException("수강생 전체 조회 중 Error 발생!! ", e);
        }
    }

    // 관리자의 수강생 정보 수정
    public boolean updateStudentinfo(Long userNo, String newName, boolean status) {
        try {
            return userDAO.updateStudentinfo(userNo, newName, status);
        } catch (SQLException e) {
            throw new RuntimeException("수강생 찾는 중 에러 발생 !!" + e);
        }
    }

    public boolean updateAmount(Long userNo, Long amount) {
        try {
            return userDAO.updateAmount(userNo, amount);
        } catch (SQLException e) {
            throw new RuntimeException("사용자 보유금액 업데이트 중 오류!" + e);
        }
    }


    // 관리자 비활성화 수강생 조회
    public List<UserDTO> findInactiveStudents() {
        try {
            return userDAO.findInactiveStudents();
        } catch (SQLException e) {
            throw new RuntimeException("비활성 수강생 조회 중 오류 발생!!", e);
        }
    }

    // 관리자의 비활성화 수강생 삭제
    public boolean deleteStudent(Long userNo) {

        try {
            // DAO에서 boolean 반환
            return userDAO.deleteUser(userNo);
        } catch (SQLException e) {
            throw new RuntimeException("수강생 삭제 중 오류 발생", e);
        }
    }

    // 관리자의 강사 전체 조회
    public List<UserDTO> findAllInstructor() {
        try {
            return userDAO.findAllInstructor();
        } catch (SQLException e) {
            throw new RuntimeException("강사 전체 조회 중 Error 발생!! ", e);
        }
    }

    // 관리자의 강사 정보 수정
    public boolean updateInstructorinfo(Long userNo, String newName, boolean status) {
        try {
            return userDAO.updateInstructorinfo(userNo, newName, status);
        } catch (SQLException e) {
            throw new RuntimeException("강사 찾는 중 에러 발생 !!" + e);
        }
    }
    // 관리자의 비활성화 강사 조회
    public List<UserDTO> findInactiveInstructors() {
        try {
            return userDAO.findInactiveInstructors();
        } catch (SQLException e) {
            throw new RuntimeException("비활성 강사 조회 중 오류 발생!!", e);
        }
    }

    // 관리자의 비활성화 강사 삭제
    public boolean deleteInstructor(Long userNo) {

        try {
            return userDAO.deleteInstructor(userNo);
        } catch (SQLException e) {
            throw new RuntimeException("강사 삭제 중 오류 발생", e);
        }
    }

}