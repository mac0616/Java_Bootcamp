package com.wanted.crud.payment.model.dao;

import com.wanted.crud.course.model.dto.CourseDTO;
import com.wanted.crud.global.utils.PaymentQueryUtil;
import com.wanted.crud.payment.model.dto.PaymentDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {
    private final Connection connection;

    public PaymentDAO(Connection connection) { this.connection = connection;}

    public List<PaymentDTO> selectAll() throws SQLException {
        String query = PaymentQueryUtil.getQuery("payment.selectAll");
        List<PaymentDTO> paymentList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                PaymentDTO payment = new PaymentDTO(
                        rset.getLong("payment_id"),
                        rset.getLong("payment_amount"),
                        rset.getString("payment_method"),
                        rset.getBoolean("status"),
                        rset.getDate("paid_at"),
                        rset.getLong("student_id"),
                        rset.getLong("course_id")
                );
                paymentList.add(payment);
            }
            return paymentList;
        }
    }


    //세이브 (새로운 결제추가)
    public Long save(PaymentDTO newPayment) throws SQLException {
        String query = PaymentQueryUtil.getQuery("payment.save");

        try (PreparedStatement pstmt = connection.prepareStatement(query , PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setLong(1, newPayment.getPaymentAmount());
            pstmt.setString(2, newPayment.getPaymentMethod());
            pstmt.setBoolean(3, newPayment.isStatus());
            pstmt.setLong(4, newPayment.getStudentId());
            pstmt.setLong(5, newPayment.getCourseId());

            // dml 구문은 executeUpdate 를 통해 query 를 실행한다.
            // 결과 값은 정수 자료형 즉 영향을 받은 행의 갯수가 리턴된다.
            int affectedRows = pstmt.executeUpdate();
            if(affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if(rs.next()) {
                    return rs.getLong(1); //pk값 쫘르륵
                }
            }
        }

        return null;


    }

    // 강좌별 누적 결제 총금액 정산
    public List<PaymentDTO> selectRevenueByCourse() throws SQLException {
        String query = PaymentQueryUtil.getQuery("payment.getRevenueByCourse");
        List<PaymentDTO> revenueList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rset = pstmt.executeQuery()) {

            while (rset.next()) {
                PaymentDTO revenueInfo = new PaymentDTO(
                        rset.getLong("course_id"),
                        rset.getString("course_title"),
                        rset.getLong("total_revenue")
                );
                revenueList.add(revenueInfo);
            }
        }
        return revenueList;
    }










}
