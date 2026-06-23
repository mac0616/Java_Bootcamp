package com.wanted.crud.userView;

import com.wanted.crud.Application;
import com.wanted.crud.enrollment.controller.EnrollmentController;
import com.wanted.crud.enrollment.model.dto.EnrollmentStudentDTO;
import com.wanted.crud.enrollment.view.EnrollmentInputView;

import java.util.List;
import java.util.Scanner;

import static com.wanted.crud.Application.*;


public class StudentMenu {
    private Scanner sc = new Scanner(System.in);
    private String role;
    private Long userNo;
    private Long studentId;


    public StudentMenu(Scanner sc, String role, Long userNo, Long studentId) {
        this.sc = sc;
        this.role = role;
        this.userNo = userNo;
        this.studentId = studentId;
    }

    public void showMenu() {
        boolean isStudentLoggedIn = true;

        while (isStudentLoggedIn) {
            System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("  🧑‍🎓 [ STUDENT DASHBOARD ]");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("  1. 📚 강좌 조회 및 신청");
            System.out.println("  2. 👤 마이페이지");
            System.out.println("  3. ✍️ 리뷰 작성");
            System.out.println("  4. ⚠️ 회원 탈퇴");
            System.out.println("  0. 🔙 로그아웃");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.print("  ▶ 메뉴를 선택해주세요: ");

            int menu = getMenuInput();

            switch (menu) {
                case 1: courseInquiryMenu(); break;
                case 2: myPageScreen(); break;
                case 3: reviewScreen(); break;
                case 4:
                    boolean isDeleted = userInputView.deleteUser();
                    if (isDeleted) {
                        isStudentLoggedIn = false; // ✅ 성공했을 때만 로그아웃
                    }
                    break;
                case 0:
                    System.out.println("\n  👋 [로그아웃] 안전하게 로그아웃 되었습니다. 다음에 또 봐요!");
                    isStudentLoggedIn = false;
                    break;
                default:
                    System.out.println("  ❌ [오류] 잘못된 입력입니다. 다시 선택해주세요.");
                    break;
            }
        }
    }

    private void courseInquiryMenu() {
        boolean isCourseMenuOpen = true;
        while (isCourseMenuOpen) {
            System.out.println("\n  📂 [ 1. 강좌 조회 세부 메뉴 ]");
            System.out.println("  --------------------------------------------------");
            System.out.println("  • 1. ✨ 새로운 강좌 신청하기");
            System.out.println("  • 2. 📖 수강 중인 강좌 보기");
            System.out.println("  . 3. ⭐ 강좌 리뷰 보기");
            System.out.println("  • 0. 🔙 뒤로가기");
            System.out.println("  --------------------------------------------------");
            System.out.print("  ▶ 메뉴를 선택해주세요: ");

            int menu = getMenuInput();

            switch (menu) {
                case 1: courseApplyMenu(); break;
                case 2: takingCoursesScreen(); break;
                case 3: courseReviewScreen(); break;
                case 0: isCourseMenuOpen = false; break;
                default: System.out.println("  ❌ [오류] 잘못된 입력입니다."); break;
            }
        }
    }

    private void courseReviewScreen() {
        courseInputView.courseReviewScreen();
    }


    //수강 강좌. 수강 조회 -> 수강 결제 -> 성공시 등록
    private void courseApplyMenu() {
        String paymentMethod = null;
        boolean status;
        long studentCash;
        courseInputView.viewAllCourses();
        System.out.println("어느 강좌를 신청하시겠습니까?");
        System.out.print("신청할 강좌의 강좌번호를 입력해주세요.");
        Long courseId = (long)getMenuInput();
        //중복강좌 신청불가 로직
        //중복강좌 신청했으면 리턴
        //+없는 강좌도 처리해야함.
        if(!courseInputView.isCourseExists(courseId)) {
            System.out.println("해당 강좌는 없거나 오픈되지않았습니다.");
            return;
        }
        if(enrollmentInputView.isStudyingCourse(studentId, courseId)) {
            System.out.println("이미 수강신청한 강좌입니다.");
            return;
        }
        studentCash = userInputView.getAmount(userNo);//강좌가격


        System.out.println("\n  💳 [결제진행]");
        System.out.println("결제 수단을 입력해주세요.");
        System.out.println("1. 신용카드 2.계좌이체 3. 핸드폰결제");
        int methodId = getMenuInput();
        long paymentAmount;
        paymentAmount = courseInputView.getPrice(courseId);
        System.out.println("[보유금액]: " + studentCash + "[강좌가격]: " + paymentAmount);
        switch (methodId) {
            case 1:
                paymentMethod = "creditCard";
                break;
            case 2:
                paymentMethod = "accountTransfer";
                break;
            case 3:
                paymentMethod = "phonePayment";
                break;
        }

        //수강생이 보유한 금액이 결제금액보다 많은지 확인.
        if(studentCash > paymentAmount) {
            status = true;
            System.out.println("결제할 수 있음!");
            //수강생 보유금액에서 결제금액 차감
            //userInputView.updateAmount(userNo, 차감될 금액)
            try {userInputView.updateAmount(userNo, -paymentAmount);}
            catch(Exception e) {
                System.out.println("결제 도중 문제가 발생했습니다." + e);
                return;
            }
            //수강생의 수강내역에 저장
            enrollmentInputView.createEnrollment(studentId, courseId);
            //결제내역에 저장.
            paymentInputView.createPayment(paymentAmount, paymentMethod, status, studentId, courseId);
        } else {
            System.out.println("금액부족! 결제할 수 없음.");
        }

    }

    private void takingCoursesScreen() {

        System.out.println("\n  📖 [ 나의 수강 목록 ]");
        System.out.println("  📡  현재 수강 중인 강좌 리스트를 불러오는 중입니다...");
        enrollmentInputView.studentCoursePage(studentId);

        // 강좌 수강하기
        System.out.println("강좌를 수강하시겠습니까?");
        System.out.println("1. 예    2. 아니오");
        System.out.print(" ▶ 선택: ");
        int takeSection = getMenuInput();
        if (takeSection == 1) {
            System.out.print("\n수강할 강좌의 강좌 번호를 입력해주세요 : ");
            long courseId = (long) getMenuInput();

            boolean updateProgress = enrollmentInputView.updateEnrollmentProgress(studentId, courseId);

            if (updateProgress) {
                System.out.println("강좌 수강이 완료되었습니다! (진척도 +20)");
            } else {
                System.out.println("🚨 강좌 수강에 실패하였습니다.");
                System.out.println("🚨 이미 수강 완료한 강좌거나, 잘못된 강좌 번호 입니다.");
            }
        } else {
            System.out.println("이전 화면으로 돌아갑니다.");
        }
    }

    private void myPageScreen() {
        System.out.println("\n  👤 [ 수강생 마이페이지 ]");
        userInputView.studentMyPage(userNo);
    }

    private void reviewScreen() {
        System.out.println("\n  ✍️ [ 강의 리뷰 작성 ]");
        System.out.println("  📝  수강 완료된 강좌를 조회하여 따끈따끈한 후기를 남겨주세요!");
        Application.courseInputView.reviewScreen(studentId);
    }

    // 공통 입력 예외 처리 메서드
    private int getMenuInput() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}