package com.wanted.crud.user.model.dao;

import com.wanted.crud.global.utils.UserQueryUtil;
import com.wanted.crud.user.model.dto.InstructorDTO;
import com.wanted.crud.user.model.dto.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private final Connection connection;

    public UserDAO(Connection connection) {this.connection = connection;}

    //selectAll
    public List<UserDTO> selectAll() throws SQLException {
        String query = UserQueryUtil.getQuery("users.selectAll");
        List<UserDTO> userList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                UserDTO user = new UserDTO(
                        rset.getLong("user_no"),
                        rset.getString("user_id"),
                        rset.getString("user_password"),
                        rset.getString("user_name"),
                        rset.getString("user_phone_number"),
                        rset.getLong("user_price"),
                        rset.getString("user_role"),
                        rset.getDate("created_at"),
                        rset.getBoolean("status")
                );

                userList.add(user);
            }
        }
        return userList;
    }

    // 아이디 찾기
    public String findId(String name, String phone_number) throws SQLException {
        String query = UserQueryUtil.getQuery("users.findId");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, phone_number);

            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                return rset.getString("user_id"); // 비밀번호 리턴
            }
        }
        return null;
    }

    //마이페이지 조회
    public UserDTO findSelectUserNo(long userNo) throws SQLException {
        String query = UserQueryUtil.getQuery("users.findSelectUserNo");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, userNo);

            //select 결과는 ResultSet 객체로 변환!!
            ResultSet rset = pstmt.executeQuery();

            if(rset.next()) {
                return new UserDTO(
                        rset.getLong("user_no"),
                        rset.getString("user_id"),
                        rset.getString("user_password"),
                        rset.getString("user_name"),
                        rset.getString("user_phone_number"),
                        rset.getLong("user_price"),
                        rset.getString("user_role"),
                        rset.getDate("created_at"),
                        rset.getBoolean("status")
                );
            }
        }
        return null;
    }

    public UserDTO findSelectUserRole(String userRole) throws SQLException {
        String query = UserQueryUtil.getQuery("users.findSelectUserRole");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, userRole);

            //select 결과는 ResultSet 객체로 변환!!
            ResultSet rset = pstmt.executeQuery();

            if(rset.next()) {
                return new UserDTO(
                        rset.getLong("user_no"),
                        rset.getString("user_id"),
                        rset.getString("user_password"),
                        rset.getString("user_name"),
                        rset.getString("user_phone_number"),
                        rset.getLong("user_price"),
                        rset.getString("user_role"),
                        rset.getDate("created_at"),
                        rset.getBoolean("status")
                );
            }
        }
        return null;
    }

    // 비번 찾기
    public String findPassword(String userid, String phone_number) throws SQLException {
        String query = UserQueryUtil.getQuery("users.findPassword");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, userid);
            pstmt.setString(2, phone_number);

            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                return rset.getString("user_password"); // 비밀번호 리턴
            }
        }
        return null;
    }




    public UserDTO login(String id, String password) throws SQLException {

        String query = UserQueryUtil.getQuery("users.login");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, id);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new UserDTO(
                        //  여기에 user_no 도 getString
                        rs.getLong("user_no"),
                        rs.getString("user_id"),
                        rs.getString("user_password"),
                        rs.getString("user_role")

                );
            }
        }
        return null;
    }

    // Insert문
    public Long save(UserDTO newUser) throws SQLException {
        // 1. properties 등에서 INSERT 쿼리를 가져옵니다.
        // 예상 쿼리: INSERT INTO `USER` (user_id, user_password, user_name, user_phone_number, user_role) VALUES (?, ?, ?, ?, ?)
        String query = UserQueryUtil.getQuery("users.insert");
        Long generatedId = null;

        // 2. INSERT 실행 후 생성된 PK값을 가져오기 위해 RETURN_GENERATED_KEYS 옵션을 추가합니다.
        try (PreparedStatement pstmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // 3. UserDTO의 데이터들을 쿼리의 ?(파라미터)에 바인딩합니다.
            // DB에 설정한 DEFAULT 값(user_price, created_at, status)은 생략하고 필수 값만 넣는다고 가정했습니다.
            pstmt.setString(1, newUser.getUserId());
            pstmt.setString(2, newUser.getUserPassword());
            pstmt.setString(3, newUser.getUserName());
            pstmt.setString(4, newUser.getUserPhoneNumber());
            pstmt.setString(5, newUser.getUserRole());

            // 4. INSERT 쿼리를 실행하고 영향을 받은 행(row)의 개수를 반환받습니다.
            int affectedRows = pstmt.executeUpdate();

            // 5. 성공적으로 INSERT 되었다면, 생성된 PK(user_no)를 조회하여 반환합니다.
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedId = generatedKeys.getLong(1);
                    }
                }
            }
        }

        return generatedId; // 성공 시 생성된 유저번호 반환, 실패 시 null 반환
    }

    //  회원탈퇴 update문
    public boolean dropUser(String userId, String password) throws SQLException {
        // 1. status 컬럼을 false로 변경
        String query = UserQueryUtil.getQuery("users.drop");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, userId);
            pstmt.setString(2, password);

            int affectedRows = pstmt.executeUpdate();

            // 3. 1개 이상 행이 변경되었으면 탈퇴 성공
            return affectedRows > 0;
        }
    }


    public List<UserDTO> findInstructorByName(String name) throws SQLException {
        List<UserDTO> instructors = new ArrayList<>();

        String query = UserQueryUtil.getQuery("user.findInstructorByName");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, "%" + name + "%"); // 부분 일치 검색

            try (ResultSet rset = pstmt.executeQuery()) {
                while (rset.next()) {
                    UserDTO user = new UserDTO(
                            rset.getLong("user_no"),
                            rset.getString("user_id"),
                            rset.getString("user_password"),
                            rset.getString("user_name"),
                            rset.getString("user_phone_number"),
                            rset.getLong("user_price"),
                            rset.getString("user_role"),
                            rset.getTimestamp("created_at"),
                            rset.getBoolean("status")
                    );

                    instructors.add(user);
                }
            }
        } catch (SQLException e) {
            System.err.println("강사 이름 조회 중 오류 발생: " + e.getMessage());
            throw e;
        }

        return instructors;
    }

    public Long getAmount(Long userNo) throws SQLException {
        String query = UserQueryUtil.getQuery("user.getAmount");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, userNo);

            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                return rset.getLong("user_price");

            }
        }
        return null;
    }

    // 유저 보유금액 수정하기 (유저 번호, 변경할 금액)
    public boolean updateAmount(Long userNo, Long userAmount) throws SQLException{

        String query = UserQueryUtil.getQuery("user.updateAmount");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, userAmount);  // 변경할 가격
            pstmt.setLong(2, userNo);     // 변경할 대상

            int result = pstmt.executeUpdate();

            return result > 0; // 1이면 성공, 0이면 실패

        } catch (SQLException e) {
            System.err.println("수강생 수정 중 오류 발생: " + e.getMessage());
            return false;
        }
    }

    public List<UserDTO> findAllStudents() throws SQLException{
        List<UserDTO> list = new ArrayList<>();

        String query = UserQueryUtil.getQuery("user.findAllStudents");

        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rset = pstmt.executeQuery()) {

            while (rset.next()) {
                UserDTO user = new UserDTO(
                        rset.getLong("user_no"),
                        rset.getString("user_id"),
                        rset.getString("user_password"),
                        rset.getString("user_name"),
                        rset.getString("user_phone_number"),
                        rset.getLong("user_price"),
                        rset.getString("user_role"),
                        rset.getDate("created_at"),
                        rset.getBoolean("status")
                );

                list.add(user);
            }
        }
        return list;
    }


    // 관리자의 수강생 정보 수정
    public boolean updateStudentinfo(Long userNo, String newName, boolean status) throws SQLException{

        String query = UserQueryUtil.getQuery("user.updateStudentInfo");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, newName);   // 변경할 이름
            pstmt.setBoolean(2, status);  // 변경할 상태
            pstmt.setLong(3, userNo);     // 대상 user_no

            int result = pstmt.executeUpdate();

            return result > 0; // 1이면 성공, 0이면 실패

        } catch (SQLException e) {
            System.err.println("수강생 수정 중 오류 발생: " + e.getMessage());
            return false;
        }
    }


    // 관리자의 비활성화 수강생 조회

    public List<UserDTO> findInactiveStudents() throws SQLException {

        List<UserDTO> list = new ArrayList<>();

        String query = UserQueryUtil.getQuery("findInactiveStudents");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                UserDTO user = new UserDTO(
                         rset.getLong("user_no"),
                        rset.getString("user_id"),
                        rset.getString("user_password"),
                        rset.getString("user_name"),
                        rset.getString("user_phone_number"),
                        rset.getLong("user_price"),
                        rset.getString("user_role"),
                        rset.getDate("created_at"),
                        rset.getBoolean("status")
                );

                list.add(user);
            }
        }

        return list;
    }

    // 관리자의 수강생 삭제
    public boolean deleteUser(Long userNo) throws SQLException {

        String query = UserQueryUtil.getQuery("users.delete");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setLong(1, userNo);

            int result = pstmt.executeUpdate();

            return result > 0; // 1 이상이면 삭제 성공
        }
    }

    // 관리자의 강사 전체 조회
    public List<UserDTO>  findAllInstructor() throws SQLException {

        List<UserDTO> list = new ArrayList<>();

        String query = UserQueryUtil.getQuery("findAllInstructor");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                UserDTO user = new UserDTO(
                        rset.getLong("user_no"),
                        rset.getString("user_id"),
                        rset.getString("user_password"),
                        rset.getString("user_name"),
                        rset.getString("user_phone_number"),
                        rset.getLong("user_price"),
                        rset.getString("user_role"),
                        rset.getDate("created_at"),
                        rset.getBoolean("status")
                );

                list.add(user);
            }
        }

        return list;
    }
    // 관리자의 강사 정보 수정
    public boolean updateInstructorinfo(Long userNo, String newName, boolean status) throws SQLException{

        String query = UserQueryUtil.getQuery("user.updateInstructorInfo");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, newName);   // 변경할 이름
            pstmt.setBoolean(2, status);  // 변경할 상태
            pstmt.setLong(3, userNo);     // 대상 user_no

            int result = pstmt.executeUpdate();

            return result > 0; // 1이면 성공, 0이면 실패

        } catch (SQLException e) {
            System.err.println("수강생 수정 중 오류 발생: " + e.getMessage());
            return false;
        }
    }
    // 관리자의 비활성화 강사 조회
    public List<UserDTO> findInactiveInstructors() throws SQLException {

        List<UserDTO> list = new ArrayList<>();

        String query = UserQueryUtil.getQuery("findInactiveInstructors");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                UserDTO user = new UserDTO(
                        rset.getLong("user_no"),
                        rset.getString("user_id"),
                        rset.getString("user_password"),
                        rset.getString("user_name"),
                        rset.getString("user_phone_number"),
                        rset.getLong("user_price"),
                        rset.getString("user_role"),
                        rset.getDate("created_at"),
                        rset.getBoolean("status")
                );

                list.add(user);
            }
        }

        return list;
    }
    // 관리자의 비활성화 강사 삭제
    public boolean deleteInstructor(Long userNo) throws SQLException {

        String query = UserQueryUtil.getQuery("users.delete");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setLong(1, userNo);

            int result = pstmt.executeUpdate();

            return result > 0; // 1 이상이면 삭제 성공
        }
    }

}

