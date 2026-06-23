package com.wanted.crud.payment.model.service;

import com.wanted.crud.course.model.dto.CourseDTO;
import com.wanted.crud.payment.model.dao.PaymentDAO;
import com.wanted.crud.payment.model.dao.ToSettlementPaymentDAO;
import com.wanted.crud.payment.model.dto.PaymentDTO;
import com.wanted.crud.payment.model.dto.ToSettlementPaymentDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PaymentService {
    private final PaymentDAO paymentDAO;
    private final ToSettlementPaymentDAO toSettlementPaymentDAO;
    private final Connection connection;
    public PaymentService(Connection connection) {
        this.connection = connection;
        this.paymentDAO = new PaymentDAO(connection);
        this.toSettlementPaymentDAO = new ToSettlementPaymentDAO(connection);
    }



    public List<PaymentDTO> selectAllPayment() {
        try {
            return paymentDAO.selectAll();
        } catch (SQLException e) {
            throw new RuntimeException("결제 전체 조회 중 Error 발생!! /PaymentService" + e);
        }
    }

    public Long savePayment(PaymentDTO newPayment) {
        try {
            return paymentDAO.save(newPayment);
        } catch (SQLException e) {
            throw new RuntimeException("결제 내역 등록 중 Error 발생!!! 🚨" + e);
        }
    }

    // 강좌별 누적 총금액 계산
    public List<PaymentDTO> getRevenueByCourse() {
        try {
            return paymentDAO.selectRevenueByCourse();
        } catch (SQLException e) {
            throw new RuntimeException("🚨 강좌별 수익 조회 중 오류 발생: " + e.getMessage());
        }
    }
}
