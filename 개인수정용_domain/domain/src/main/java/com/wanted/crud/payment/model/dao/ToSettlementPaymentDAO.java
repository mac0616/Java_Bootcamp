package com.wanted.crud.payment.model.dao; // 패키지명은 상황에 맞게 수정하세요

import com.wanted.crud.global.utils.PaymentQueryUtil;
import com.wanted.crud.payment.model.dto.PaymentDTO;
import com.wanted.crud.payment.model.dto.ToSettlementPaymentDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ToSettlementPaymentDAO {
    private final Connection connection;

    public ToSettlementPaymentDAO(Connection connection) {
        this.connection = connection;
    }

    // 🌟 수정: 시작 날짜(startDate)와 종료 날짜(endDate)를 매개변수로 받습니다.
    public List<ToSettlementPaymentDTO> selectForSettlement(Timestamp startDate) throws SQLException {
        String query = PaymentQueryUtil.getQuery("payment.selectForSettlement");
        List<ToSettlementPaymentDTO> paymentList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            // 쿼리에 '?'가 하나뿐이므로 index 1에만 값을 세팅합니다.
            pstmt.setTimestamp(1, startDate);

            try (ResultSet rset = pstmt.executeQuery()) {
                while (rset.next()) {
                    // SQL의 별칭인 "raw_amount"를 사용하여 값을 가져옵니다.
                    ToSettlementPaymentDTO forStmPay = new ToSettlementPaymentDTO(
                            rset.getLong("raw_amount"), // <- 수정된 부분
                            rset.getLong("course_id")
                    );
                    paymentList.add(forStmPay);
                }
            }
            return paymentList;
        }
    }
}