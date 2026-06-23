package com.wanted.crud.user.model.dao;

import com.wanted.crud.global.utils.UserQueryUtil;
import com.wanted.crud.user.model.dto.StudentDTO;
import com.wanted.crud.user.model.dto.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    private final Connection connection;

    public StudentDAO(Connection connection) {this.connection = connection;}
    public List<StudentDTO> selectAll() throws SQLException {

        String query = UserQueryUtil.getQuery("student.selectAll");
        List<StudentDTO> studentList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                StudentDTO student = new StudentDTO(
                        rset.getLong("student_id"),
                        rset.getLong("user_no")
                );

                studentList.add(student);
            }
        }

        return studentList;
    }


    //student_id 값으로 학생찾기. 혹은 user_no로 변경할수도 있음.
    public StudentDTO findById(long id) throws SQLException {
        String query = UserQueryUtil.getQuery("student.findById");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, id);

            //select 결과는 ResultSet 객체로 변환!!
            ResultSet rset = pstmt.executeQuery();

            if(rset.next()) {
                return new StudentDTO(
                        rset.getLong("student_id"),
                        rset.getLong("user_no")
                );
            }
        }
        return null;
    }

    //마이페이지 수정/삭제
    public int updateStudent(UserDTO user) throws SQLException {
        String query = UserQueryUtil.getQuery("student.update");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            /* comment.
             * 수정할 컬럼(title, description, status)을 먼저 세팅하고,
             * 마지막에 WHERE 조건에 들어갈 course_id를 세팅합니다.
             * (실제 properties나 xml의 쿼리문 ? 순서에 맞춰야 합니다)
             * */
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getUserPassword());
            pstmt.setString(3, user.getUserPhoneNumber());
            pstmt.setLong(4, user.getUserNo());

            // 수정한 행(row)의 갯수를 반환
            return pstmt.executeUpdate();
        }
    }

    public Long findId(Long userNo) throws SQLException {
        String query = UserQueryUtil.getQuery("student.findId");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, userNo);

            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                return rset.getLong("student_id");

            }
        }
        return null;
    }




}