package com.wanted.crud.userView;

import com.wanted.crud.Application;
import com.wanted.crud.user.view.UserInputView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

import static com.wanted.crud.Application.enrollmentController;
import static com.wanted.crud.Application.userInputView;

public class MainInput {
    private static final Logger log = LoggerFactory.getLogger(MainInput.class);
    private Scanner sc = new Scanner(System.in);
    public String role;
    public Long userNo;
    public Long instructorId;
    public Long studentId;
    public void startApp() {
        while (true) {
            System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("  🌟 [ WELCOME TO WANTED SKILLS ONLINE ]");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("  1. 🔑 로그인");
            System.out.println("  2. 📝 회원가입");
            System.out.println("  3. 🔍 ID/Password 찾기");
            System.out.println("  4. ❌ 프로그램 종료");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.print("  ▶ 메뉴 선택: ");

            int startMenu = -1;
            try {
                startMenu = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  🚨 [입력오류] 숫자로 된 메뉴 번호를 입력해주세요.\n");
                continue;
            }

            switch (startMenu) {
                case 1: loginScreen(); break;
                case 2: registerScreen(); break;
                case 3: findIdScreen(); break;
                case 4:
                    System.out.println("\n  👋 이용해주셔서 감사합니다. 프로그램을 종료합니다.");
                    sc.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("  ❌ [오류] 잘못된 선택입니다. 다시 입력해주세요.\n");
                    break;
            }
        }
    }

    private void loginScreen() {
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  🔑 [ 로그인 시스템 접속 ]");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.print("  ▶ 아이디 입력: ");
        String id = sc.nextLine().trim();
        System.out.print("  ▶ 비밀번호 입력: ");
        String password = sc.nextLine().trim();

        userNo = userInputView.loginGetNo(id, password);
        if (userNo == null) {
            System.out.println("  🚨 로그인 정보를 다시 확인해주세요.");
            return;
        }

        role = userInputView.loginSession(id, password);

// 🔥 역할별로 분기해서 조회
        if ("INSTRUCTOR".equals(role)) {
            instructorId = userInputView.instructorId(userNo);
        } else if ("STUDENT".equals(role)) {
            studentId = userInputView.studentFindId(userNo);
        }

        System.out.println("\n  ✨ [인증성공] " + id + "님, 환영합니다!");
        System.out.println("  📡 권한 확인: " + role);
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        switch (role) {
            case "STUDENT":
                new StudentMenu(sc, role, userNo, studentId).showMenu();
                break;
            case "INSTRUCTOR":
                new InstructorMenu(sc, role, userNo, instructorId).showMenu();
                break;
            case "ADMIN":
                new AdminMenu(sc, role, userNo, instructorId).showMenu();
                break;
            default:
                System.out.println("  🚨 [오류] 존재하지 않는 역할이거나 권한이 만료되었습니다.\n");
                break;
        }
    }

    private void registerScreen() {
        System.out.println("\n  📝 [ 회원가입 프로세스 시작 ]");
        userInputView.displayRegister();
    }

    private void findIdScreen() {
        System.out.println("\n  🔍 [ 계정 정보 찾기 모드 ]");
        userInputView.findIdPassword();
    }
}