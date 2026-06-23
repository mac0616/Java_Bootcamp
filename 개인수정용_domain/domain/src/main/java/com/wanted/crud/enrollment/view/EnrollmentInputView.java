package com.wanted.crud.enrollment.view;

import com.wanted.crud.enrollment.controller.EnrollmentController;
import com.wanted.crud.enrollment.model.dto.EnrollmentStudentDTO;
import com.wanted.crud.payment.controller.PaymentController;
import com.wanted.crud.payment.view.PaymentOutputView;

import java.util.List;
import java.util.Scanner;

public class EnrollmentInputView {
    /* comment.
     *   View 계층의 책임
     *   - 사용자의 입력 or 출력을 담당한다.
     *   - InputView 의 할 일
     *   - 사용자가 고를 수 있는 메뉴를 출력한다.
     *   - Scanner 를 활용한 입력을 처리한다.
     *   - Controller 를 사용자 입력에 맞게 호출한다.
     *   - OutputView 를 호출하여 결과를 출력할 수 있게 한다.
     *   .
     *   - InputView 가 하면 안 되는 일
     *   - SQL 작성 X
     *   - 비즈니스 로직 처리 X
     *   - Commit / Rollback X
     * */

    private final EnrollmentController controller;
    private final EnrollmentOutputView outputView;

    public EnrollmentInputView(EnrollmentController controller, EnrollmentOutputView outputView) {
        this.controller = controller;
        this.outputView = outputView;
    }


    public void studentCoursePage(Long studentId) {
        controller.studentCoursePage(studentId);

    }

    public boolean createEnrollment(Long studentId, Long courseId) {
        if(controller.createEnrollment(studentId, courseId)) {
            System.out.println("수강신청 완료!");
            return true;
        } else {
            System.out.println("수강신청 실패!");
        }
        return false;
    }

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

    // 중복 수강인지 아닌지 체크.
    public boolean isStudyingCourse(long studentId, long courseId) {
        return controller.isStudyingCoruse(studentId, courseId);
    }


    private final Scanner sc = new Scanner(System.in);



    public boolean updateEnrollmentProgress(Long studentId, Long courseId){
        return controller.updateEnrollmentProgress(studentId, courseId);
    }
}