package com.wanted.crud.user.view;

import com.wanted.crud.user.controller.UserController;
import com.wanted.crud.user.model.dto.UserDTO;
import java.util.List;
import java.util.Scanner;

public class AdminInputView {

    private final UserController controller;
    private final UserOutputView outputView;
    private final Scanner sc = new Scanner(System.in);

    public AdminInputView(UserController controller, UserOutputView outputView) {
        this.controller = controller;
        this.outputView = outputView;
    }

    // 관리자의 강사 이름 조회
    public void searchInstructor() {
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        outputView.printMessage("🔍 [ 관리자 전용: 강사 이름으로 검색 ]");

        System.out.print("▶ 조회할 강사의 이름을 입력하세요: ");
        String name = inputString();


        List<UserDTO> instructorList = controller.findInstructorByName(name);

        // 결과 출력
        if (instructorList != null && !instructorList.isEmpty()) {
            outputView.printMessage("📊 '" + name + "' 검색 결과 리포트");
            outputView.printInstructors2(instructorList);
        } else {
            outputView.printError("🚨 [조회결과없음] '" + name + "' 강사가 존재하지 않습니다.");
        }
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }

    //관리자의 강사 정보 전체 조회 후 수정
    public void updateInstructor(){

        // 1. 전체 강사 출력 (status 상관없이)
        List<UserDTO> list = controller.findAllInstructor();
        outputView.printUsers(list);

        // 2. user_no 입력
        System.out.print("수정할 강사의 user_no를 입력해주세요: ");
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
        boolean result = controller.updateInstructorinfo(userNo, newName, status);

        // 5. 결과 출력
        if (result) {
            outputView.printSuccess("강사 정보가 수정되었습니다.");
        } else {
            outputView.printError("수정 실패 (user_no 확인 필요)");
        }
    }

    // 관리자의 강사 정보 삭제
    public void deleteInstructor() {

        // 1. status = 0 강사 출력
        List<UserDTO> list = controller.findInactiveInstructors();
        outputView.printUsers(list);

        if (list == null || list.isEmpty()) {
            outputView.printError("삭제할 강사가 없습니다.");
            return;
        }

        // 2. user_no 입력
        System.out.print("삭제할 강사의 user_no를 입력해주세요: ");
        Long userNo = Long.parseLong(inputString());


        // 3. 삭제 실행
        boolean result = controller.deleteInstructor(userNo);

        // 4. 결과 출력
        if (result) {
            outputView.printSuccess("강사가 완전히 삭제되었습니다.");
        } else {
            outputView.printError("삭제 실패 (user_no 확인 필요)");
        }
    }

    private int inputInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("숫자만 입력해주세요.");
            }
        }
    }




    private String inputString() {
        return sc.nextLine();
    }
}