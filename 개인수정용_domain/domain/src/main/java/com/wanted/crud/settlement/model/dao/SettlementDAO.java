package com.wanted.crud.settlement.model.dao;

import com.wanted.crud.global.utils.SettlementQueryUtil;
import com.wanted.crud.global.utils.UserQueryUtil;
import com.wanted.crud.settlement.model.dto.SettlementDTO;
import com.wanted.crud.user.model.dto.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// 강사가 select 하면 나오도록.
public class SettlementDAO {

    private final Connection connection;

    public SettlementDAO(Connection connection) {
        this.connection = connection;
    }

    public SettlementDTO find(long id) throws SQLException {

        String query = SettlementQueryUtil.getQuery("settlement.findById");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setLong(1, id);

            // select 결과는 ResultSet 객체로 반환!!
            ResultSet rset = pstmt.executeQuery();

            if(rset.next()) {
                return new SettlementDTO(
                        rset.getLong("settlement_id"),
                        rset.getString("status"),
                        rset.getDate("settlement_date"),
                        rset.getLong("instructor_id"),
                        rset.getLong("course_id")
                );
            }
        }
        return null;
    }

    // 강사별 총수익
    public List<SettlementDTO> selectRevenueByInstructor() throws SQLException {
        String query = SettlementQueryUtil.getQuery("settlement.getRevenueByInstructor");
        List<SettlementDTO> revenueList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rset = pstmt.executeQuery()) {
//            while (rset.next()) {
//                revenueList.add(new SettlementDTO(
//                        rset.getLong("instructor_id"),
//                        rset.getString("instructor_name"),
//                        rset.getLong("total_revenue")
//                ));
//            } //수정하긴해야함.
        }
        return revenueList;
    }

    // 정산 전체 조회
    public List<SettlementDTO> viewAllSettlement() throws SQLException {
        String query = SettlementQueryUtil.getQuery("settlement.ViewAllSettlement");
        List<SettlementDTO> List = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                SettlementDTO settlement = new SettlementDTO(
                        rset.getLong("settlement_id"),
                        rset.getLong("course_id"),
                        rset.getTimestamp("settlement_date"),
                        rset.getLong("instructor_id"),
                        rset.getLong("raw_amount"),
                        rset.getLong("commission"),
                        rset.getLong("final_amount"),
                        rset.getString("status")
                );

                List.add(settlement);
            }
        }
        return List;
    }

    // 미정산 조회
    public List<SettlementDTO> viewWaitSettlement() throws SQLException{
        String query = SettlementQueryUtil.getQuery("settlement.ViewWaitSettlement");
        List<SettlementDTO> List = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                SettlementDTO settlement = new SettlementDTO(
                        rset.getLong("settlement_id"),
                        rset.getLong("course_id"),
                        rset.getTimestamp("settlement_date"),
                        rset.getLong("instructor_id"),
                        rset.getLong("raw_amount"),
                        rset.getLong("commission"),
                        rset.getLong("final_amount"),
                        rset.getString("status")
                );

                List.add(settlement);
            }
        }
        return List;
    }


    public Long save(SettlementDTO newSettlement) throws SQLException {
        // 1. properties 등에서 INSERT 쿼리를 가져옵니다.
        // 예상 쿼리: INSERT INTO `USER` (user_id, user_password, user_name, user_phone_number, user_role) VALUES (?, ?, ?, ?, ?)
        String query = SettlementQueryUtil.getQuery("settlement.save");
        Long generatedId = null;

        // 2. INSERT 실행 후 생성된 PK값을 가져오기 위해 RETURN_GENERATED_KEYS 옵션을 추가합니다.
        try (PreparedStatement pstmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {


            pstmt.setLong(1, newSettlement.getCourseId());
            pstmt.setLong(2, newSettlement.getInstructorId());
            pstmt.setLong(3, newSettlement.getRawAmount());
            pstmt.setLong(4, newSettlement.getCommssion());
            pstmt.setLong(5, newSettlement.getFinalAmount());

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

    public Long saveDone(SettlementDTO newSettlement) throws SQLException {
        // 1. properties 등에서 INSERT 쿼리를 가져옵니다.
        // 예상 쿼리: INSERT INTO `USER` (user_id, user_password, user_name, user_phone_number, user_role) VALUES (?, ?, ?, ?, ?)
        String query = SettlementQueryUtil.getQuery("settlement.saveDone");
        Long generatedId = null;

        // 2. INSERT 실행 후 생성된 PK값을 가져오기 위해 RETURN_GENERATED_KEYS 옵션을 추가합니다.
        try (PreparedStatement pstmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {


            pstmt.setLong(1, newSettlement.getCourseId());
            pstmt.setLong(2, newSettlement.getInstructorId());
            pstmt.setLong(3, newSettlement.getRawAmount());
            pstmt.setLong(4, newSettlement.getCommssion());
            pstmt.setLong(5, newSettlement.getFinalAmount());

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


    // 정산 상태 업데이트 (예: WAIT -> DONE)
    public void updateStatus(Long settlementId, String status) throws SQLException {
        // properties 파일에 "UPDATE settlement SET status = ? WHERE settlement_id = ?" 쿼리를 추가하거나 직접 작성
        String query = SettlementQueryUtil.getQuery("settlement.updateStatus");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, status);
            pstmt.setLong(2, settlementId);

            int result = pstmt.executeUpdate();
            if (result == 0) {
                System.out.println("🚨 상태 업데이트 실패: 해당 정산 ID를 찾을 수 없습니다. (" + settlementId + ")");
            }
        }
    }
}
