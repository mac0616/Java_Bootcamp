package com.wanted.crud.user.view;

import com.wanted.crud.enrollment.model.dto.EnrollmentStudentDTO;
import com.wanted.crud.user.controller.UserController;
import com.wanted.crud.user.model.dto.UserDTO;

import java.util.List;
import java.util.Scanner;

public class UserInputView {

    private final UserController controller;
    private final UserOutputView outputView;

    private final Scanner sc = new Scanner(System.in);

    public UserInputView(UserController controller,
                         UserOutputView outputView) {
        this.controller = controller;
        this.outputView = outputView;
    }

    // 로그인
    //

    public String loginSession(String id, String password) {
        UserDTO loginUser = controller.login(id, password);

        if (loginUser == null) {
            outputView.printError("아이디 또는 비밀번호가 틀렸습니다.");
        }

        String role = null;
        if (loginUser != null) {
            role = loginUser.getUserRole();
        }
        return role;
    }




    // 로그인해서 유저번호 리턴
    public Long loginGetNo(String id, String password) {
        UserDTO loginUser = controller.login(id, password);

        if (loginUser == null) {
            outputView.printError("user_no가 없습니다.");
        }

        Long user_no = null;
        if (loginUser != null) {
            user_no = loginUser.getUserNo();
        }
        return user_no;
    }

    // 회원가입
    public void displayRegister() {
        while (true) {
            System.out.println();
            System.out.println("=================[회원 가입]==============");
            System.out.print("사용할 아이디를 입력해주세요: ");
            String id = inputString();
            System.out.print("사용할 비밀번호 입력해주세요: ");
            String password = inputString();
            System.out.print("사용할 이름을 입력해주세요: ");
            String name = inputString();


            // 🔥 전화번호 검증
            String phone_number = "";
            while (true) {
                System.out.print("사용할 전화번호를 입력해주세요 (ex: 010-1234-5678) : ");
                phone_number = inputString();

                // 숫자와 하이픈만 허용 (010-1234-5678 형태)
                if (phone_number.matches("^\\d{3}-\\d{4}-\\d{4}$")) {
                    break; // 정상 입력
                } else {
                    System.out.println("❌ 올바른 형식의 전화번호를 입력해주세요. (예: 010-1234-5678)");
                }
            }

            // 🔥 역할 선택
            String role = "";
            while (true) {
                System.out.print("역할을 선택해주세요: 1. 학생  2. 강사 : ");
                int choice = inputInt();

                switch (choice) {
                    case 1:
                        role = "STUDENT";
                        break;
                    case 2:
                        role = "INSTRUCTOR";
                        break;
                    default:
                        System.out.println("❌ 올바른 번호를 입력해주세요.");
                        continue; // 다시 입력
                }
                break; // 정상 입력 시 탈출
            }

            Long result = controller.createUser(id, password, name, phone_number, role);

            if (result != null && result > 0) {
                outputView.printSuccess("회원가입 성공! 생성된 계정 ID : " + result);
                break; // 회원가입 빠져나옴
            } else {
                outputView.printError("회원가입 실패");
                break;
            }
        }
    }
    // 수강생 마이페이지 보기
    public void studentMyPage(Long userNo) {
        System.out.println("1. 내 정보 보기 2. 내 정보 수정하기");
        int menu = inputInt();
        switch (menu) {
            case 1:
                findSelectUserNo(userNo);
                break;
            case 2:
                updateStudent(userNo);
                break;
        }
    }

    // 강사 마이페이지
    public void instructorMyPage(Long userNo) {
        System.out.println("1. 내 정보 보기 2. 내 정보 수정하기");
        int menu = inputInt();
        switch (menu) {
            case 1:
                findSelectUserNo(userNo);
                break;
            case 2:
                updateInstructor(userNo);
                break;
        }
    }
    // 사용자님의 정보찾기
    public void findSelectUserNo(Long userNo) {
        System.out.println("사용자님의 정보입니다.");
        System.out.println(controller.findSelectUserNo(userNo));
    }

    public void findSelectUserRole(String userRole) {
        System.out.println(userRole + "의 정보입니다.");
        System.out.println(controller.findSelectUserRole(userRole));
    }
    // 아이디, 비번 찾기
    public void findIdPassword() {
        while (true) {
            System.out.println();
            System.out.println("===== 아이디/비밀번호 찾기 메뉴 =====");
            System.out.println("1. 아이디 찾기");
            System.out.println("2. 비밀번호 찾기");
            System.out.println("0. 뒤로가기");
            System.out.print("선택: ");

            int choice = inputInt(); // 기존에 만든 inputInt() 사용

            switch (choice) {
                case 1:
                    // 아이디 찾기
                    displayFindId();
                    break;
                case 2:
                    // 비밀번호 찾기
                    displayFindPassword();
                    break;
                case 0:
                    // 메뉴 종료
                    return;
                default:
                    outputView.printError("잘못된 입력입니다. 0~2 사이로 입력해주세요.");
            }
        }
    }
    // 아이디 찾기
    public void displayFindId() {
        System.out.println("이름을 입력하세요");
        String name = inputString();
        System.out.println("전화번호를 입력하세요 ex) 010-1234-5678");
        String phonenumber = inputString();


        String userid = controller.findId(name, phonenumber);

        if (userid != null) {
            System.out.println("아이디: " + userid); // 아이디 출력
        } else {
            outputView.printError("아이디와 전화번호가 일치하는 사용자가 없습니다.");
        }

    }

    //user no를 입력하면 강사번호가 나온다.
    public Long instructorId(Long userNo) {
        return controller.instructorFindId(userNo);
    }

    // 비밀번호 찾기
    public void displayFindPassword() {
        System.out.println("아이디를 입력하세요");
        String userid = inputString();
        System.out.println("전화번호를 입력하세요 ex) 010-1234-5678");
        String phonenumber = inputString();

        String password = controller.findPassword(userid, phonenumber);

        if (password != null) {
            System.out.println("비밀번호: " + password); // 비밀번호 출력
        } else {
            outputView.printError("아이디와 전화번호가 일치하는 사용자가 없습니다.");
        }

    }
    // 유저(수강생) 정보 수정(주체: 수강생)
    public void updateStudent(Long userNo) {
        System.out.println("변경할 이름을 입력하세요: ");
        String name = inputString();

        System.out.println("변경할 비밀번호를 입력하세요");
        String password = inputString();
        System.out.println("변경할 전화번호를 입력하세요");
        String phoneNumber = inputString();
        if(controller.updateStudent(new UserDTO(
                userNo, null, password, name, phoneNumber, null, null, null, true
        ))) {
            outputView.printMessage("학생 정보 업데이트 완료");;
        } else {
            outputView.printError("학생 정보 업데이트 중 오류 발생");
        }
    }

    // 유저(강사) 정보 수정(주체: 강사)
    public void updateInstructor(Long userNo) {
        System.out.println("변경할 이름을 입력하세요: ");
        String name = inputString();

        System.out.println("변경할 비밀번호를 입력하세요");
        String password = inputString();
        System.out.println("변경할 전화번호를 입력하세요");
        String phoneNumber = inputString();
        if(controller.updateInstructor(new UserDTO(
                userNo, null, password, name, phoneNumber, null, null, null, true
        ))) {
            outputView.printMessage("강사 정보 업데이트 완료");;
        } else {
            outputView.printError("강사 정보 업데이트 중 오류 발생");
        }
    }
    // 회원탈퇴
    public boolean deleteUser() {
        System.out.println("회원 탈퇴를 진행합니다.");

        // 1. 아이디 입력
        System.out.print("아이디를 입력하세요: ");
        String id = inputString();

        // 2. 비밀번호 입력
        System.out.print("비밀번호를 입력하세요: ");
        String password = inputString();

        // 3. Controller 호출
        boolean result = controller.dropUser(id, password); // Controller에서 boolean 반환

        /*
        // 4. 결과 출력
        if (result) {
            System.out.println("✅ 회원 탈퇴가 완료되었습니다.");
        } else {
            outputView.printError("회원 탈퇴 실패: 아이디 또는 비밀번호가 틀렸거나, 이미 탈퇴한 계정일 수 있습니다.");
        }
    }
    */
        if (result) {
            System.out.println("✅ 회원 탈퇴 완료");
            return true; // 🔥 성공
        } else {
            System.out.println("🚨 [ERROR] ❌ 회원 탈퇴 실패: 아이디 또는 비밀번호가 틀렸거나, 이미 탈퇴한 계정일 수 있습니다.");
            return false; // 🔥 실패
        }
    }
    // 관리자 메서드
    // 관리자의 강좌별 수강생 조회

    public void viewStudentBycourseId(){

        List<EnrollmentStudentDTO> list = controller.viewStudentBycourseId();

        outputView.printEnrollmentStudents(list);
    }

    //학생번호
    public Long studentFindId(Long userNo) {
        return controller.studentFindId(userNo);
    }

    public Long getAmount(Long userNo) {
        return controller.getAmount(userNo);
    }


    // 유저 정보 삭제

    //


    public void studentMenu(UserDTO loginUser) {
        while (true) {
            outputView.printMenu();
            int choice = inputInt();

            switch (choice) {
                case 1:
                    break;
                case 2:
                    break;
                case 0:
                    return;
                default:
                    outputView.printError("잘못된 입력입니다.");
            }
        }
    }


    public void instructorMenu(UserDTO loginUser) {
        while (true) {
            outputView.printInstructorMenu();
            int choice = inputInt();

            switch (choice) {
                case 1:
                    break;
                case 2:
                    break;
                case 0:
                    return;
                default:
                    outputView.printError("잘못된 입력입니다.");
            }
        }
    }


    public void adminMenu() {
        while (true) {
            outputView.printAdminMenu();
            int choice = inputInt();

            switch (choice) {
                case 1:
                    outputView.printUsers(controller.selectAllUsers());
                    break;
                case 2:
                    outputView.printStudents(controller.selectAllStudents());
                    break;
                case 3:
                    outputView.printInstructors(controller.selectAllInstructors());
                    break;
                case 0:
                    return;
                default:
                    outputView.printError("잘못된 입력입니다.");
            }
        }
    }
//=========================================================
    //사용자 보유금액 업데이트
    public void updateAmount(Long userNo, Long amount) {
        System.out.println("값 업데이트중입니다");
        if(controller.updateAmount(userNo, amount)) {
            System.out.println("!!값 업데이트 완료");
        }
        else {
            System.out.println("ㅠㅠ 업데이트 실패했습니다 ㅠㅠ");
        }
    }


//    =======================================================



    // ===== 입력 =====
    private String inputString() {
        return sc.nextLine();
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
    }




    // 관리자의 수강생 정보 삭제
    public void deleteStudent() {

        // 1. status = 0 수강생 출력
        List<UserDTO> list = controller.findInactiveStudents();
        outputView.printUsers(list);

        if (list == null || list.isEmpty()) {
            outputView.printError("삭제할 수강생이 없습니다.");
            return;
        }

        // 2. user_no 입력
        System.out.print("삭제할 수강생의 user_no를 입력해주세요: ");
        Long userNo = Long.parseLong(inputString());

        // 3. 삭제 실행
        boolean result = controller.deleteStudent(userNo);

        // 4. 결과 출력
        if (result) {
            outputView.printSuccess("수강생이 완전히 삭제되었습니다.");
        } else {
            outputView.printError("삭제 실패 (user_no 확인 필요)");
        }
    }

}