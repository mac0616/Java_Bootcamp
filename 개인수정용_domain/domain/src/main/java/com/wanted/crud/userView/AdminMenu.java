package com.wanted.crud.userView;

import com.wanted.crud.Application;

import java.util.Scanner;

import static com.wanted.crud.Application.*;

public class AdminMenu {
    private Scanner sc = new Scanner(System.in);
    private String role;
    private Long userNo;
    private Long instructorId;

    public AdminMenu(Scanner sc, String role, Long userNo, Long instructorId) {
        this.sc = sc;
        this.role = role;
        this.userNo = userNo;
        this.instructorId = instructorId;
    }

    public AdminMenu(Scanner sc) {
        this.sc = sc;
    }

    public void showMenu() {
        boolean isAdminLoggedIn = true;

        while (isAdminLoggedIn) {
            System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("  👑 [ SYSTEM ADMINISTRATOR CONTROL PANEL ]");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("  1. 🧑‍🎓 수강생 관리");
            System.out.println("  2. 👨‍🏫 강사 관리");
            System.out.println("  3. 📚 강좌 관리");
            System.out.println("  4. 📊 대시보드");
            System.out.println("  5. 💰 정산하기");
            System.out.println("  0. 🔙 로그아웃");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.print("  ▶ 메뉴를 선택해주세요: ");

            int menu = -1;
            try {
                menu = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  🚨 [오류] 숫자로 된 메뉴 번호를 입력해주세요.");
                continue;
            }

            switch (menu) {
                case 1: adminManageStudentsMenu(); break;
                case 2: adminManageInstructorsMenu(); break;
                case 3: adminManageCoursesMenu(); break;
                case 4: adminDashboardMenu(); break;
                case 5: adminSettlementScreen(); break;
                case 0:
                    System.out.println("\n  👋 [로그아웃] 안전하게 시스템을 종료합니다.");
                    isAdminLoggedIn = false;
                    break;
                default:
                    System.out.println("  ❌ [오류] 잘못된 메뉴 선택입니다. 다시 입력해주세요.");
                    break;
            }
        }
    }

    private void adminManageStudentsMenu() {
        boolean isMenuOpen = true;
        while (isMenuOpen) {
            System.out.println("\n  📂 [ 1. 수강생 관리 세부 메뉴 ]");
            System.out.println("  --------------------------------------------------");
            System.out.println("  • 1. 🔍 강좌별 수강생 조회");
            System.out.println("  • 2. 🛠️ 수강생 정보 수정");
            System.out.println("  • 3. 🛠️ 수강생 정보 삭제");
            System.out.println("  • 0. 🔙 뒤로가기");
            System.out.println("  --------------------------------------------------");
            System.out.print("  ▶ 메뉴를 선택해주세요: ");

            int menu = getMenuInput();
            if (menu == 1) userInputView.viewStudentBycourseId();
            else if (menu == 2) {System.out.println("\n  🛠️ [알림] 수강생 수정 로직 준비 중...");

                userInputView.updateStudent();

            }
            else if (menu == 3) {System.out.println("\n  🛠️ [알림] 수강생 삭제 로직 준비 중...");
                userInputView.deleteStudent();
            }
            else if (menu == 0) isMenuOpen = false;
            else System.out.println("  ❌ [오류] 잘못된 선택입니다.");
        }
    }

    private void adminManageInstructorsMenu() {
        boolean isMenuOpen = true;
        while (isMenuOpen) {
            System.out.println("\n  📂 [ 2. 강사 관리 세부 메뉴 ]");
            System.out.println("  --------------------------------------------------");
            System.out.println("  • 1. 🔍 이름으로 강사 조회");
            System.out.println("  • 2. 🛠️ 강사 정보 수정");
            System.out.println("  • 3. 🛠️ 강사 정보 삭제");
            System.out.println("  • 0. 🔙 뒤로가기");
            System.out.println("  --------------------------------------------------");
            System.out.print("  ▶ 메뉴를 선택해주세요: ");

            int menu = getMenuInput();
            if (menu == 1) adminInputView.searchInstructor();
            else if (menu == 2) {System.out.println("\n  🛠️ [알림] 강사 정보 수정 로직 준비 중...");
              adminInputView.updateInstructor();
            }
            else if (menu == 3) {System.out.println("\n  🛠️ [알림] 강사 정보 삭제 로직 준비 중...");
            adminInputView.deleteInstructor();
            }
            else if (menu == 0) isMenuOpen = false;
            else System.out.println("  ❌ [오류] 잘못된 선택입니다.");
        }
    }

    private void adminManageCoursesMenu() {
        boolean isMenuOpen = true;
        while (isMenuOpen) {
            System.out.println("\n  📂 [ 3. 강좌 관리 세부 메뉴 ]");
            System.out.println("  --------------------------------------------------");
            System.out.println("  • 1. 🔍 강사 이름으로 강좌 조회");
            System.out.println("  • 2. 🛠️ 정보 수정 및 삭제");
            System.out.println("  • 0. 🔙 뒤로가기");
            System.out.println("  --------------------------------------------------");
            System.out.print("  ▶ 메뉴를 선택해주세요: ");

            int menu = getMenuInput();
            if (menu == 1) {
                if (Application.courseInputView != null) Application.courseInputView.viewInstructorCourses();
                else System.out.println("  🚨 [시스템 오류] 강좌 뷰가 초기화되지 않았습니다.");
            } else if (menu == 2) {
                if (com.wanted.crud.Application.courseInputView != null) com.wanted.crud.Application.courseInputView.adminManageCourseDetail();
                else System.out.println("  🚨 [시스템 오류] 강좌 뷰가 초기화되지 않았습니다.");
            } else if (menu == 0) isMenuOpen = false;
            else System.out.println("  ❌ [오류] 잘못된 선택입니다.");
        }
    }

    private void adminDashboardMenu() {
        boolean isMenuOpen = true;
        while (isMenuOpen) {
            System.out.println("\n  📊 [ 4. 대시보드 세부 메뉴 ]");
            System.out.println("  --------------------------------------------------");
            System.out.println("  • 1. 💰 강좌별 총 결제금액");
            System.out.println("  • 2. 💸 강사별 누적 총수익");
            System.out.println("  • 3. 📈 플랫폼 전체 총수익");
            System.out.println("  • 0. 🔙 뒤로가기");
            System.out.println("  --------------------------------------------------");
            System.out.print("  ▶ 메뉴를 선택해주세요: ");

            int menu = getMenuInput();
            if (menu == 1) {
                if (Application.paymentInputView != null) {
                    Application.paymentInputView.viewRevenueByCourse();
                } else {
                    System.out.println("  🚨 [시스템 오류] 결제 관련 화면을 불러올 수 없습니다.");
                }
            }

            else if (menu == 2) {
                if (settlementInputView != null) {
                    settlementInputView.viewRevenueByInstructor();
                } else {
                    System.out.println("  🚨 [시스템 오류] 정산 관련 화면을 불러올 수 없습니다.");
                }
            }
            else if (menu == 3) System.out.println("\n  📈 [통계] 플랫폼 전체 수익을 합산합니다...");
            else if (menu == 0) isMenuOpen = false;
            else System.out.println("  ❌ [오류] 잘못된 선택입니다.");
        }
    }

    private void adminSettlementScreen() {
        boolean isMenuOpen = true;
        while (isMenuOpen) {
        System.out.println("\n  💰 [ 5. 정산 관리 시스템 ]");
        System.out.println("  --------------------------------------------------");
        System.out.println(" 1. 전체 정산 내역 보기. 2. create 미정산된 결제내역 , select 불러오기 3. 정산내역 처리하기");
        //2. 정산내역 불러오면 status WAIT 인 상태로 불러와지고 3. WAIT 인 상태의 정산내역들이 관리자, 강사 돈이들어감.
        System.out.println("  🛠️  [처리중] 강사료 및 수수료 정산 로직 가동 중...");
        System.out.println("  ✅  정산 완료 시 상태가 업데이트 됩니다.");
        System.out.println("  --------------------------------------------------");

            int menu = getMenuInput();

            // 1. 전체 정산 내역 보기
            if (menu == 1) {
                System.out.println("전체 정산 내역 보기 로직");
                settlementInputView.viewAllSettlement();
            }

            else if(menu == 2){
                System.out.println("create 미정산된 결제내역 조회 로직");
                // Java 8 이상 (권장 방식)
                java.sql.Timestamp now = java.sql.Timestamp.valueOf(java.time.LocalDateTime.now());
                settlementInputView.paymentToSettlement(now);
            } // 진도 파이팅!

            else if (menu == 3) {
                System.out.println("정산내역 처리하기 로직");
                settlementInputView.processFullSettlement();
            }

            else if (menu == 0) isMenuOpen = false;
            else System.out.println("  ❌ [오류] 잘못된 선택입니다.");
        }

    }


    private int getMenuInput() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

}