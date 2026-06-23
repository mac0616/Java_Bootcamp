package com.wanted.crud;

import com.wanted.crud.course.controller.CourseController;
import com.wanted.crud.course.model.service.CourseService;
import com.wanted.crud.course.view.CourseInputView;
import com.wanted.crud.course.view.CourseOutputView;
import com.wanted.crud.enrollment.controller.EnrollmentController;
import com.wanted.crud.enrollment.model.service.EnrollmentService;
import com.wanted.crud.enrollment.view.EnrollmentInputView;
import com.wanted.crud.enrollment.view.EnrollmentOutputView;
import com.wanted.crud.global.config.JDBCTemplate;
import com.wanted.crud.payment.controller.PaymentController;
import com.wanted.crud.payment.model.service.PaymentService;
import com.wanted.crud.payment.view.PaymentInputView;
import com.wanted.crud.payment.view.PaymentOutputView;
import com.wanted.crud.settlement.controller.SettlementController;
import com.wanted.crud.settlement.model.service.SettlementService;



import com.wanted.crud.settlement.view.SettlementInputView;
import com.wanted.crud.settlement.view.SettlementOutputView;
import com.wanted.crud.user.controller.UserController;
import com.wanted.crud.user.model.service.UserService;
import com.wanted.crud.user.view.AdminInputView;
import com.wanted.crud.user.view.UserInputView;
import com.wanted.crud.user.view.UserOutputView;
import com.wanted.crud.userView.MainInput;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Application {



    public static CourseInputView courseInputView;
    public static EnrollmentInputView enrollmentInputView;
    public static SettlementInputView settlementInputView;
    public static UserInputView userInputView;
    public static AdminInputView adminInputView;
    public static PaymentInputView paymentInputView;
    public static EnrollmentController enrollmentController;


    public static void main(String[] args) {
        try (Connection con = JDBCTemplate.getConnection()) {

            System.out.println("✅ 데이터베이스 연결 성공!!");

            // 객체 조립 진행
            CourseService courseService = new CourseService(con);
            CourseController courseController = new CourseController(courseService);
            CourseOutputView courseOutputView = new CourseOutputView();
            CourseInputView courseinputView = new CourseInputView(courseController, courseOutputView);

            EnrollmentService enrollmentService = new EnrollmentService(con);
            EnrollmentController enrollmentController = new EnrollmentController(enrollmentService);
            EnrollmentOutputView enrollmentOutputView = new EnrollmentOutputView();
            EnrollmentInputView enrollmentinputview = new EnrollmentInputView(enrollmentController, enrollmentOutputView);



            SettlementService settlementService = new SettlementService(con);
            SettlementController settlementController = new  SettlementController(settlementService);
            SettlementOutputView settlementOutputView = new  SettlementOutputView();
            SettlementInputView settlementinputView = new  SettlementInputView(settlementController, settlementOutputView);

            PaymentService paymentService = new PaymentService(con);
            PaymentController paymentController = new PaymentController(paymentService);
            PaymentOutputView paymentOutputView = new PaymentOutputView();
            PaymentInputView paymentinputview = new PaymentInputView(paymentController, paymentOutputView);

            UserService userService = new UserService(con);
            UserController userController = new UserController(userService);
            UserOutputView userOutputView = new UserOutputView();
            UserInputView userinputView = new UserInputView(userController, userOutputView);
            AdminInputView adminInputViewLocal = new AdminInputView(userController, userOutputView);



            // 조립이 끝난 뷰를 전역 변수에 저장
            courseInputView = courseinputView;
            enrollmentInputView = enrollmentinputview;
            settlementInputView = settlementinputView;
            userInputView = userinputView;
            paymentInputView = paymentinputview;
            adminInputView = adminInputViewLocal;

            // ❌ 삭제: 프로그램 켜지자마자 강좌 조회하던 테스트 코드는 지웁니다.
            // inputView.viewMyCourse();

            // ★ 추가: Scanner를 생성하고 강사 메뉴를 화면에 띄웁니다!
            Scanner sc = new Scanner(System.in);
            MainInput mainmenu = new MainInput();
            mainmenu.startApp();
        } catch (SQLException e) {
            System.err.println("🚨 데이터베이스 연결 실패...");
        } finally {
            JDBCTemplate.close();
        } //
    }
}
