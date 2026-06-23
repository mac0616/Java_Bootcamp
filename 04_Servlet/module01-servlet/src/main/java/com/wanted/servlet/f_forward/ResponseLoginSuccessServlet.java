package com.wanted.servlet.f_forward;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;

@WebServlet("/print")
public class ResponseLoginSuccessServlet extends HttpServlet {

    /* comment.
    *   forward 받은 서블릿에서도 요청 방식이 get이면 doGet,
    *   post 이면 doPost를 오버라이딩 해야 한다.
    * */

    // 사용자 응답 특화 서블릿
    // 최초 요청을 post로 받아서 응답 시에도 post 사용.
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userId = req.getAttribute("userId").toString();
        System.out.println("/print 서블릿이 전달 받은 userId : " + userId);

        // 실제 동작하는건 "/pring" 인데 주소창에는 "/forward"로 나옴.
        // 이게 '하는척'임. (forward의 역할)
        StringBuilder responseText = new StringBuilder();
        responseText.append("<!doctype html>\n")
                .append("<html>\n")
                .append("<head>\n")
                .append("</head>\n")
                .append("<body>\n")
                .append("<h3 align=\"center\">")
                .append(userId)
                .append("님 환영합니다.</h3>")
                .append("</body>\n")
                .append("</html>\n");

        resp.setContentType("text/html; charset=UTF-8");

        PrintWriter out = resp.getWriter();

        out.print(responseText.toString());

        out.flush();
        out.close();
    }
}
