package com.wanted.crud;

import com.wanted.crud.course.controller.CourseController;
import com.wanted.crud.course.model.service.CourseService;
import com.wanted.crud.course.view.CourseInputView;
import com.wanted.crud.course.view.CourseOutputView;
import com.wanted.crud.global.config.JDBCTemplate;

import java.sql.Connection;
import java.sql.SQLException;

public class Application {

    public static void main(String[] args) {
        // JDBCTemplate에서 throw함 그래서 try-with-resource -> catch문으로
        try (Connection con = JDBCTemplate.getConnection()) {

            System.out.println("✅ 데이터베이스 연결 성공!!");
            JDBCTemplate.printConnectionStatus();

            /* comment.
            *   Application -> CouseInputView -> CourseController -> CourseService
            *   -> CourseDAO -> MySQL(RDBMS)
            *   response 시(응답 시) : 역순이다. 다만 CourseOutView 를 통해 결과물을 보여줄 것이다.
            *   + 데이터에 접근할 수 있는건 DAO뿐. view에서 절대 데이터에 접근 할 수 없음.
            *   + 트랜잭션은 필요에 따라 Service에서 작성함.. (롤백해야 하니까)
            * */

            // /***/ = 문서화 주석 : deprecated = 레거시 한 코드니까 나중에는 사라진다는 의미.
            /**
             * @deprecated 현재 아래에 작성될 코드는 나중에는 사라지는 코드
             * 객체 조립 진행
             * */
            CourseService service = new CourseService(con);
            CourseController controller = new CourseController(service);
            CourseOutputView outputView = new CourseOutputView();
            CourseInputView inputView = new CourseInputView(controller, outputView);

            /* Application 이 실행되면 View 메소드 호출한다. */
            inputView.displayMainMenu();

        } catch (SQLException e) {
            System.err.println("🚨 데이터베이스 연결 실패...");   // err = 로그에서 에러로 보임.
        } finally {
            JDBCTemplate.close();
        }

    }

}
