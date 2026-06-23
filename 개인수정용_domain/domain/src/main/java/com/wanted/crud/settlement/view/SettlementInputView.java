package com.wanted.crud.settlement.view;

import com.wanted.crud.settlement.controller.SettlementController;
import com.wanted.crud.settlement.model.dto.SettlementDTO;
import com.wanted.crud.user.model.dto.UserDTO;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

public class SettlementInputView {

    private final SettlementController controller;
    private final SettlementOutputView outputView;
    private final Scanner sc = new Scanner(System.in);

    public SettlementInputView(SettlementController controller, SettlementOutputView outputView) {
        this.controller = controller;
        this.outputView = outputView;
    }

    private void displaySettlement() {

    }

    // 강사별 총수익
    public void viewRevenueByInstructor() {
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        // OutputView가 있다면 outputView.printMessage()를 쓰셔도 됩니다.
        System.out.println("  💸 [ 대시보드 : 강사별 누적 총수익 ]");

        List<SettlementDTO> revenueList = controller.getRevenueByInstructor();

        if (revenueList == null || revenueList.isEmpty()) {
            System.out.println("  🚨 아직 정산된 내역이 없습니다.");
        } else {
            for (SettlementDTO dto : revenueList) {
                //System.out.println(dto.toInstructorDashboardString());
            }
        }
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }


    // 정산하기 (수정할 것임)
    public void saveSettlement() {
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  📝 [ 미정산 내역 (WAIT 상태) ]");

       /* public void viewWaitSettlement() {
            List<SettlementDTO> list = controller.viewWaitSettlement();
            outputView.printAllSettlement(list);

            if (list == null || list.isEmpty()) {
                outputView.printError("조회할 정산내역이 없습니다.");
                return;
            }
        }*/
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        try {
            System.out.print("  ▶ 정산할 강사 번호를 입력하세요: ");
            Long instructorId = Long.parseLong(sc.nextLine().trim());

            System.out.print("  ▶ 정산할 강좌 번호를 입력하세요: ");
            Long courseId = Long.parseLong(sc.nextLine().trim());

            System.out.print("  ▶ 총 매출액(원금)을 입력하세요: ");
            Long rawAmount = Long.parseLong(sc.nextLine().trim());

            Long commission = (long) (rawAmount * 0.8);
            Long finalAmount = rawAmount - commission;

            System.out.println("\n  📊 [ 정산 금액 계산 완료 ]");
            System.out.println("    👨‍🏫 강사 정산금: " + commission + "원");
            System.out.println("    🏢 플랫폼 수수료: " + finalAmount + "원");

            SettlementDTO settlementDTO = new SettlementDTO(
                    null, courseId, null, instructorId, rawAmount, commission, finalAmount, "WAIT"
            );

            Long savedId = controller.saveSettlement(settlementDTO);
            if (savedId != null && savedId > 0) {
                outputView.printSuccess("💵 정산 성공!!");
            } else {
                outputView.printError("🕸️ 정산 실패..");
            }

        } catch (NumberFormatException e) {
            System.out.println("🚨 [입력 오류] 숫자만 입력하세요.");
        }
    }

    // 정산 내역 조회
    public void viewAllSettlement() {
        List<SettlementDTO> list = controller.viewAllSettlement();
        outputView.printAllSettlement(list);

        if (list == null || list.isEmpty()) {
            outputView.printError("조회할 정산내역이 없습니다.");
            return;
        }
    }
    public void paymentToSettlement(Timestamp now) {
        controller.paymentToSettlement(now);
        System.out.println("정산 페이지 갱신 완료");
    }

    public void processFullSettlement() {
        if(controller.processFullSettlement()) {
            System.out.println("정산처리 완료되었습니다.");;
        }
    }


/*
    // 관리자의 수강생 정보 수정
    public void updateStudent() {

        // 1. 전체 수강생 출력 (status 상관없이)
        List<UserDTO> list = controller.findAllStudents();
        outputView.printAllStudents(list);

        if (list == null || list.isEmpty()) {
            outputView.printError("수정할 수강생이 없습니다.");
            return;
        }

        // 2. user_no 입력
        System.out.print("수정할 수강생의 user_no를 입력해주세요: ");
        Long userNo = Long.parseLong(inputString());

        // 3. 변경 값 입력
        System.out.print("변경할 이름을 입력하세요: ");
        String newName = inputString();


        int statusInput;
        while (true) {
            System.out.print("변경할 계정 상태를 입력해주세요 (1: 활성, 0: 비활성): ");
            statusInput = inputInt();

            if (statusInput == 1 || statusInput == 0) {
                break;
            }

            System.out.println("❌ 1 또는 0만 입력 가능합니다.");
        }

        boolean status = (statusInput == 1);


        // 4. 업데이트 실행
        boolean result = controller.updateStudentinfo(userNo, newName, status);

        // 5. 결과 출력
        if (result) {
            outputView.printSuccess("수강생 정보가 수정되었습니다.");
        } else {
            outputView.printError("수정 실패 (user_no 확인 필요)");
        }
    }*/

}


