package com.wanted.crud.payment.model.dao;

import com.wanted.crud.global.utils.PaymentQueryUtil;
import com.wanted.crud.payment.model.dto.RefundDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RefundDAO {
    private final Connection connection;

    public RefundDAO(Connection connection) { this.connection = connection;}

    public List<RefundDTO> selectAll() throws SQLException {
        String query = PaymentQueryUtil.getQuery("refund.selectAll");
        List<RefundDTO> refundList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                RefundDTO refund = new RefundDTO(
                        rset.getLong("refund_id"),
                        rset.getLong("refund_amount"),
                        rset.getBoolean("refund_status"),
                        rset.getDate("refund_at"),
                        rset.getLong("payment_id")
                );
                refundList.add(refund);
            }
            return refundList;
        }
    }
}
