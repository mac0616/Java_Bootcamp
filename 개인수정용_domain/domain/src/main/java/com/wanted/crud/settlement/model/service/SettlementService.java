package com.wanted.crud.settlement.model.service;

import com.wanted.crud.payment.model.dao.ToSettlementPaymentDAO;
import com.wanted.crud.payment.model.dto.ToSettlementPaymentDTO;
import com.wanted.crud.settlement.model.dao.SettlementDAO;
import com.wanted.crud.settlement.model.dto.SettlementDTO;
import com.wanted.crud.user.model.dao.InstructorDAO;
import com.wanted.crud.user.model.dao.UserDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class SettlementService {

    private final SettlementDAO settlementDAO;
    private final ToSettlementPaymentDAO toSettlementPaymentDAO;
    private final InstructorDAO instructorDAO;
    private final UserDAO userDAO; // 계좌 업데이트를 위해 추가
    private final Connection connection;

    public SettlementService(Connection connection) {
        this.connection = connection;
        this.settlementDAO = new SettlementDAO(connection);
        this.toSettlementPaymentDAO = new ToSettlementPaymentDAO(connection);
        this.instructorDAO = new InstructorDAO(connection);
        this.userDAO = new UserDAO(connection);
    }

    /**
     * 1. 기존 기능: 정산 대기 데이터 생성 (WAIT)
     * 결제 내역을 8:2로 계산하여 정산 테이블에 'WAIT' 상태로 인서트만 수행합니다.
     */
    public boolean paymentToSettlement(Timestamp now) {
        try {
            List<ToSettlementPaymentDTO> paymentList = toSettlementPaymentDAO.selectForSettlement(now);

            if (paymentList == null || paymentList.isEmpty()) {
                return false;
            }

            for (ToSettlementPaymentDTO payData : paymentList) {
                long rawAmount = payData.getRawAmount();

                // 요청하신 비율: 관리자 8(Commission) : 강사 2(FinalAmount)
                long commission = (long) (rawAmount * 0.8); // 관리자 몫
                long finalAmount = rawAmount - commission;   // 강사 몫

                // 강사 ID 조회 (주석 처리 부분 유지)
                /* Long instructorId = instructorDAO.findIdByCourseId(payData.getCourseId()); */
                Long instructorId = 1L; // 임시값

                SettlementDTO settlement = new SettlementDTO(
                        null,
                        payData.getCourseId(),
                        null,
                        instructorId,
                        rawAmount,
                        commission,     // 관리자의 돈 (80%)
                        finalAmount,    // 강사의 돈 (20%)
                        "WAIT"
                );

                settlementDAO.save(settlement);
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("결제내역에서 정산 데이터 생성 중 오류 발생", e);
        }
    }

    /**
     * 2. 추가 기능: 실제 정산 처리 (DONE)
     * 금액을 실제 유저(강사/관리자)에게 지급하고 'DONE' 상태로 저장합니다.
     */
    /**
     * 2. 수정된 기능: 실제 정산 처리 (WAIT -> DONE)
     * Settlement 테이블의 'WAIT' 상태 데이터를 가져와 정보를 추출하고 상태를 'DONE'으로 변경합니다.
     */
    public boolean processFullSettlement() {
        try {
            // 트랜잭션 시작 (데이터 정합성을 위해 유지)
            connection.setAutoCommit(false);

            // 1) 대기(WAIT) 중인 정산 내역 가져오기
            List<SettlementDTO> waitList = settlementDAO.viewWaitSettlement();

            if (waitList == null || waitList.isEmpty()) {
                System.out.println("❌ 정산 대기 중인 내역이 없습니다.");
                return false;
            }

            for (SettlementDTO settlement : waitList) {
                // 2) 필요한 데이터 변수에 저장 (각각 Long 타입)
                Long settlementId = settlement.getSettlementId();
                Long instructorId = settlement.getInstructorId();
                Long courseId = settlement.getCourseId();

                /* * =======================================================
                 * TODO: 여기에 직접 돈을 지급하는 로직을 작성해 주세요!
                 * 예) instructorId, courseId를 활용해 user 테이블의 금액 업데이트
                 * =======================================================
                 */


                // 3) 돈 지급이 끝난 후, 해당 정산 내역의 상태를 DONE으로 업데이트
                settlementDAO.updateStatus(settlementId, "DONE");
            }

            connection.commit(); // 모든 처리가 성공하면 확정
            return true;

        } catch (SQLException e) {
            try {
                connection.rollback(); // 하나라도 에러 시 전체 취소
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new RuntimeException("🚨 정산 처리 중 오류 발생 (롤백됨): " + e.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // --- 기존 기능 유지 ---

    public SettlementDTO findbyId(long id){
        try {
            return settlementDAO.find(id);
        } catch (SQLException e) {
            throw new RuntimeException("정산 조회 중 오류 발생!! 🚨");
        }
    }

    public List<SettlementDTO> getRevenueByInstructor() {
        try {
            return settlementDAO.selectRevenueByInstructor();
        } catch (SQLException e) {
            throw new RuntimeException("🚨 강사별 수익 조회 중 오류: " + e.getMessage());
        }
    }
    // 전체 정산 조회
    public List<SettlementDTO> viewAllSettlement(){
        try {
            List<SettlementDTO> settlementList = settlementDAO.viewAllSettlement();
            if (settlementList == null || settlementList.isEmpty()) {
                System.out.println("❌ 정산 내역이 없습니다.");
            }
            return settlementList;
        } catch (SQLException e) {
            throw new RuntimeException("🚨정산 목록 조회 중 Error 발생", e);
        }
    }

    // 미정산 조회
    public List<SettlementDTO> viewWaitSettlement() {
        try {
            List<SettlementDTO> settlementList = settlementDAO.viewWaitSettlement();
            if (settlementList == null || settlementList.isEmpty()) {
                System.out.println("❌ 정산 내역이 없습니다.");
            }
            return settlementList;
        } catch (SQLException e) {
            throw new RuntimeException("🚨정산 목록 조회 중 Error 발생", e);
        }
    }

    public Long saveSettlement(SettlementDTO saveNewSettlement){
        try {
            Long saveId = settlementDAO.save(saveNewSettlement);
            if (saveId == null){
                System.out.println("🚨 정산 내역 저장에 실패했습니다.");
            }
            return saveId;
        } catch (SQLException e) {
            throw new RuntimeException("🚨 정산 내역 저장 중 Error 발생", e);
        }
    }


}