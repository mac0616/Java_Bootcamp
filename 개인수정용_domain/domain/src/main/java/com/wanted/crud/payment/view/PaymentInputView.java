package com.wanted.crud.payment.view;

import com.wanted.crud.payment.controller.PaymentController;
import com.wanted.crud.payment.model.dto.PaymentDTO;

import java.util.List;
import java.util.Scanner;

public class PaymentInputView {

    private final PaymentController controller;
    private final PaymentOutputView outputView;
    private final Scanner sc = new Scanner(System.in);

    // 🌟 추가: 통합 결제 로직을 가진 서비스


    // 생성자 수정: processService를 추가로 받습니다.
    public PaymentInputView(PaymentController controller, PaymentOutputView outputView) {
        this.controller = controller;
        this.outputView = outputView;
    }

    /**
     * [수강 신청 및 결제 통합 프로세스]
     * StudentMenu에서 호출하며, 실제 로직은 processService에게 위임하고
     * View는 그 결과에 따른 출력만 담당합니다.
     */
    public void processPaymentView(Long userNo, Long studentId, Long courseId, String paymentMethod, long paymentAmount) {

        System.out.println("\n  🔄 [시스템] 결제 및 수강 등록 처리를 시작합니다...");

        // 🌟 서비스 호출하여 결과값(String)을 받음
        // (서비스 코드가 String을 반환하도록 수정되었다고 가정하거나,
        // 기존 boolean 반환형에 맞춰 아래처럼 작성합니다)

    }

    // 기존 메서드들...
    public void allPayment() {
        System.out.println(controller.selectAllUsers());
    }

    public void createPayment(Long paymentAmount, String paymentMethod, boolean status, Long studentId, Long courseId) {
        controller.createPayment(paymentAmount, paymentMethod, status, studentId, courseId);
    }

    public void viewRevenueByCourse() {
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        outputView.printMessage("📊 [ 대시보드 : 강좌별 누적 총 결제금액 ]");

        List<PaymentDTO> revenueList = controller.getRevenueByCourse();

        if (revenueList == null || revenueList.isEmpty()) {
            outputView.printError("아직 결제된 내역이 없습니다.");
        } else {
            for (PaymentDTO dto : revenueList) {
                System.out.println(dto.toCourseDashboardString());
            }
        }
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }
}