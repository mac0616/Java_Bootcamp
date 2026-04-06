package com.wanted.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/* comment.
*   인터셉터를 구현하기 위해서는 HandlerInterceptor 를 상속받아야 한다.
*   해당 클래스는 Interceptor 로 등록이 되며, 컨트롤러의 실행 전/후를
*   가로챌 수 있는 권한을 가지게 된다.
*  */


// interceptor 은 spring 영역. bean 등록해야 함. 애매 할 때는 component
@Component
public class StopWatchInterceptor implements HandlerInterceptor {

    /* comment.
    *   preHandle 전처리
    *   컨트롤러의 핸들러메소드가 동작하기 전에 호출된다.
    * */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle 메서드 호출됨..");

        long startTime = System.currentTimeMillis();

        request.setAttribute("startTime" , startTime);

        // true : 컨트롤러의 핸들러 메서드를 이어서 호출한다.
        // false : 컨트롤러의 핸들러 메서드를 호출하지 않는다.
        return true;    // true 면 controller로 지나감.
    }

    /* comment.
     *   postHandle 후처리
     *   컨트롤러의 핸들러메소드가 동작한 후에 호출된다.
     * */
    //ModelAndView 사용자 화면에서 보이는 값 넣어줄 수 있음. (HttpServletResponse 이걸도 할 수 있음)
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

        System.out.println("postHandler 호출됨..");
        // 지역변수라 값 request 로 가져옴.
        long startTime = (Long)request.getAttribute("startTime");
        request.removeAttribute("startTime");

        long endTime = System.currentTimeMillis();

//        interval = endTime - startTime
        modelAndView.addObject("interval", endTime - startTime);
    }
}
