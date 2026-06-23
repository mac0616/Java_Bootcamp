package com.wanted.crud.enrollment.model.dao;

import com.wanted.crud.enrollment.model.dto.EnrollmentDTO;
import com.wanted.crud.enrollment.model.dto.EnrollmentStudentDTO;
import com.wanted.crud.global.utils.EnrollmentQueryUtil;
import com.wanted.crud.global.utils.UserQueryUtil;
import com.wanted.crud.user.model.dto.UserDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAO {

    private final Connection connection;

    public EnrollmentDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * 신규 수강 신청 기록 저장
     */
    public boolean save(EnrollmentDTO enrollment) throws SQLException {
        String query = EnrollmentQueryUtil.getQuery("enrollment.save");

        try (PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            // progress_rate, start_date, end_date, status, student_id, course_id


            pstmt.setLong(1, enrollment.getStudentId());
            pstmt.setLong(2, enrollment.getCourseId());


            int result = pstmt.executeUpdate();

            if (result > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 특정 수강 신청 내역 조회
     */
    public EnrollmentDTO findById(long id) throws SQLException {
        String query = EnrollmentQueryUtil.getQuery("enrollment.findById");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, id);
            ResultSet rset = pstmt.executeQuery();

            if (rset.next()) {
                return new EnrollmentDTO(
                        rset.getLong("enrollment_id"),
                        rset.getLong("progress_rate"),
                        rset.getDate("start_date"),
                        rset.getDate("end_date"),
                        rset.getString("status"),
                        rset.getLong("student_id"),
                        rset.getLong("course_id")
                );
            }
        }
        return null;
    }

    // 학생의 수강중인 강좌의 아이디 리스트
    public List<Long> studyingCourse(Long studentId) throws SQLException{
        String query = EnrollmentQueryUtil.getQuery("enrollment.isStudyingCourse");
        List<Long> list = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, studentId);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                list.add(rset.getLong("course_id"));
            }
        }
        return list;
    }

    /**
     * 특정 학생의 전체 수강 목록 조회
     */
    public List<EnrollmentDTO> findByStudentId(long studentId) throws SQLException {
        String query = EnrollmentQueryUtil.getQuery("enrollment.findByStudentId");
        List<EnrollmentDTO> list = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, studentId);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                list.add(new EnrollmentDTO(
                        rset.getLong("enrollment_id"),
                        rset.getLong("progress_rate"),
                        rset.getDate("start_date"),
                        rset.getDate("end_date"),
                        rset.getString("status"),
                        rset.getLong("student_id"),
                        rset.getLong("course_id")
                ));
            }
        }
        return list;
    }

    /**
     * 수강 상태 및 진도율 업데이트
     */
    public int updateProgress(long id, long progressRate, String status) throws SQLException {
        String query = EnrollmentQueryUtil.getQuery("enrollment.updateProgress");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, progressRate);
            pstmt.setString(2, status);
            pstmt.setLong(3, id);

            return pstmt.executeUpdate();
        }
    }

    // 강좌별 수강생 조회(주체 : 관리자)
    public List<EnrollmentStudentDTO> selectStudentByCourseid() throws SQLException {

        String query = UserQueryUtil.getQuery("view.student.bycourseid");
        List<EnrollmentStudentDTO> list = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                EnrollmentStudentDTO user = new EnrollmentStudentDTO(
                        rs.getLong("course_id"),
                        rs.getString("title"),
                        rs.getLong("user_no"),
                        rs.getString("user_id"),
                        rs.getString("user_name"),
                        rs.getString("user_phone_number")
                );

                list.add(user);
            }
        }

        return list;
    }

    // 수강생 강좌 목록 출력 (주체 : 수강생)
    public List<EnrollmentDTO> studentCoursePage(Long studentId) throws SQLException {

        String query = EnrollmentQueryUtil.getQuery("enrollment.studentcourse");
        List<EnrollmentDTO> list = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setLong(1, studentId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                EnrollmentDTO user = new EnrollmentDTO(
                        rs.getLong("course_id"),
                        rs.getLong("progress_rate"),
                        rs.getString("title"),
                        studentId
                );

                list.add(user);
            }
        }

        return list;
    }


    public int updateProgress(Long studentId, Long courseId) throws SQLException {
        String query = EnrollmentQueryUtil.getQuery("enrollment.updateProgress");

        try (PreparedStatement pstmt = connection.prepareStatement(query)){
            pstmt.setLong(1, studentId);
            pstmt.setLong(2, courseId);

            return pstmt.executeUpdate();
        }
    }



}