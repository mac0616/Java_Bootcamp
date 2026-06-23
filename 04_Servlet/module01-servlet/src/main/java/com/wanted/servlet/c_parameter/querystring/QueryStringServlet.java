package com.wanted.servlet.c_parameter.querystring;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(value = "/querystring")
public class QueryStringServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /* http://localhost:8080/querystring?name=%3Bllk&age=52&birthday=2026-03-12&gender=M&national=ko&hobbies=sleep
        *  사용자가 요청을 보내면 요청과 관련된 모든 정보는
        *  HttpServletRequest 객체에 담겨 있다.
        * */
        System.out.println("doGet() 메소드 호출됨");
        String name = req.getParameter("name");
        System.out.println("name = " + name);

        // url 로 넘어오는 데이터는 모두 String 타입.
        // 값을 다른 자료형으로 담기 위해서는 parsing (번역 작업이 필요)
        int age = Integer.parseInt(req.getParameter("age"));
        System.out.println("age = " + age);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
