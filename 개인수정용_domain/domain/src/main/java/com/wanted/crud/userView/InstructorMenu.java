package com.wanted.crud.userView;

import com.wanted.crud.Application;
import com.wanted.crud.user.view.UserInputView;

import static com.wanted.crud.Application.courseInputView;
import static com.wanted.crud.Application.userInputView;
import java.util.Scanner;

public class InstructorMenu {
    private Scanner sc = new Scanner(System.in);
    private String role;
    private Long userNo;
    private Long instructorId;

    public InstructorMenu(Scanner sc, String role, Long userNo, Long instructorId) {
        this.sc = sc;
        this.role = role;
        this.userNo = userNo;
        this.instructorId = instructorId;
    }

    public InstructorMenu(Scanner sc) {
        this.sc = sc;
    }

    public void showMenu() {
        boolean isInstructorLoggedIn = true;

        while (isInstructorLoggedIn) {
            System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("  👨‍🏫 [ INSTRUCTOR DASHBOARD ]");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("  1. 🔍 내 강좌 조회");
            System.out.println("  2. 🛠️ 강좌 등록 및 관리");
            System.out.println("  3. 👤 마이페이지");
            System.out.println("  4. 🧑‍🎓 수강생 현황");
            System.out.println("  5. 💰 정산 확인");
            System.out.println("  6. ⚠️ 회원 탈퇴");
            System.out.println("  7. 🌐 전체 강좌 보기");
            System.out.println("  0. 🔙 로그아웃");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.print("  ▶ 메뉴를 선택해주세요: ");

            int menu = getMenuInput();

            switch (menu) {
                case 1: viewMyCourse(); break;
                case 2: courseManagementMenu(); break;
                case 3: myPageScreen(); break;
                case 4: studentStatusScreen(); break;
                case 5: settlementCheckScreen(); break;
                case 6:
                    userInputView.deleteUser();
                    isInstructorLoggedIn = false;
                    break;
                case 7: viewAllCourses(); break;
                case 0:
                    System.out.println("\n  👋 [로그아웃] 강사 세션을 종료합니다. 오늘도 수고하셨습니다!");
                    isInstructorLoggedIn = false;
                    break;
                default:
                    System.out.println("  ❌ [오류] 잘못된 입력입니다. 다시 선택해주세요.");
                    break;
            }
        }
    }

    private void viewMyCourse() {
        System.out.println("\n  🔎 [ 내 강좌 목록 조회 중... ]");
        if (courseInputView != null) {
            courseInputView.viewMyCourse(instructorId);
        } else {
            System.out.println("  🚨 [시스템 오류] 강좌 뷰가 초기화되지 않았습니다.");
        }
    }

    private void viewAllCourses() {
        System.out.println("\n  🌐 [ 플랫폼 전체 강좌 목록 조회 ]");
        if (courseInputView != null) {
            courseInputView.viewAllCourses();
        } else {
            System.out.println("  🚨 [시스템 오류] 강좌 뷰가 초기화되지 않았습니다.");
        }
    }

    private void courseManagementMenu() {
        boolean isMenuOpen = true;
        while (isMenuOpen) {
            System.out.println("\n  📂 [ 2. 강좌 등록 및 관리 세부 메뉴 ]");
            System.out.println("  --------------------------------------------------");
            System.out.println("  • 1. ✨ 새 강좌 등록하기");
            System.out.println("  • 2. 🗑️ 기존 강좌 삭제하기");
            System.out.println("  • 3. 📝 강좌/강의 수정하기");
            System.out.println("  • 0. 🔙 뒤로가기");
            System.out.println("  --------------------------------------------------");
            System.out.print("  ▶ 메뉴를 선택해주세요: ");

            int menu = getMenuInput();

            switch (menu) {
                case 1:
                    System.out.println("\n  🆕 [ STEP 1 : 강좌 정보 입력 ]");
                    if (courseInputView != null) {
                        courseInputView.createCourse(instructorId);
                    } else {
                        System.out.println("  🚨 시스템 오류: 뷰가 초기화되지 않았습니다.");
                        break;
                    }

                    System.out.println("\n  🎬 [ STEP 2 : 강의(Section) 등록 제안 ]");
                    System.out.println("  강좌에 포함될 세부 강의를 지금 등록하시겠습니까?");
                    System.out.println("  1. 👍 예 (강의 등록 진행) | 2. ✋ 아니오 (나중에 등록)");
                    System.out.print("  ▶ 선택: ");

                    int subMenu = getMenuInput();
                    if (subMenu == 1) {
                        courseInputView.createSection(instructorId);
                    } else {
                        System.out.println("  ✅ 강좌 기본 정보만 저장하고 메뉴로 돌아갑니다.");
                    }
                    break;

                case 2:
                    System.out.println("\n  🗑️ [ 강좌 삭제 프로세스 ]");
                    viewMyCourse();
                    System.out.print("\n  ▶ 삭제할 강좌 번호를 입력하세요 (취소: 0): ");
                    long courseIdToDelete = getLongInput();

                    if (courseIdToDelete == 0) {
                        System.out.println("  🚫 강좌 삭제를 취소했습니다.");
                    } else if (courseInputView != null) {
                        courseInputView.deleteCourse(courseIdToDelete, instructorId);
                    }
                    break;

                case 3: editCourseMenu(); break;
                case 0: isMenuOpen = false; break;
                default: System.out.println("  ❌ 잘못된 입력입니다."); break;
            }
        }
    }

    private void editCourseMenu() {
        boolean isEditMenuOpen = true;
        while (isEditMenuOpen) {
            System.out.println("\n  📝 [ 1.3 강좌 수정 상세 메뉴 ]");
            System.out.println("  --------------------------------------------------");
            System.out.println("  • 1. 📘 강좌 기본정보 수정");
            System.out.println("  • 2. 🎬 강의 세부내용 수정");
            System.out.println("  • 0. 🔙 뒤로가기");
            System.out.println("  --------------------------------------------------");
            System.out.print("  ▶ 메뉴를 선택해주세요: ");

            int menu = getMenuInput();
            switch (menu) {
                case 1:
                    System.out.println("\n  📘 [ 강좌 기본 정보 수정 모드 ]");
                    if (courseInputView != null) courseInputView.updateCourseView(instructorId);
                    break;
                case 2:
                    System.out.println("\n  🎬 [ 강의 세부 내용 수정 모드 ]");
                    if (courseInputView != null) courseInputView.updateSectionView(instructorId);
                    break;
                case 0: isEditMenuOpen = false; break;
                default: System.out.println("  ❌ 잘못된 입력입니다."); break;
            }
        }
    }

    private void myPageScreen() {
        System.out.println("\n  👤 [ 강사 마이페이지 ]");
        userInputView.instructorMyPage(userNo);
    }

    private void studentStatusScreen() {
        System.out.println("\n  🧑‍🎓 [ 수강생 현황 리포트 ]");
        System.out.println("  📊  내 강좌를 수강 중인 전체 학생 목록을 불러오는 중...");
        if (courseInputView != null) {
            courseInputView.studentStatusScreen(instructorId);
        } else {
            System.out.println("  🚨 [시스템 오류] 강좌 뷰가 초기화되지 않았습니다.");
        }

    }

    private void settlementCheckScreen() {
        System.out.println("\n  💰 [ 수익 정산 확인 ]");
        System.out.println("  💸  이번 달 정산 예정 금액 및 이력을 조회합니다.");
    }


    // 공통 입력 메서드
    private int getMenuInput() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private long getLongInput() {
        try {
            return Long.parseLong(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}