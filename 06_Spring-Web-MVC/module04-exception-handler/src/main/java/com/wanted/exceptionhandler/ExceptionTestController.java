package com.wanted.exceptionhandler;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExceptionTestController {
    @GetMapping("nullpointer")
    public String nullPointer(){
        // 고의적으로 NullPointerException 발생시킴!
        String str = null;
        System.out.println(str.toString()); // 여기서 에러 발생

        return "/";     // 밑에서 가져가서 이건 작동 X
    }

    /* comment.
    *   @ExceptionHandler(처리하고자 하는 예외) 정의를 해두면
    *   정의한 예외상황이 발생하게 되면, ExceptionHandler 가
    *   흐름을 가로채서 동작을 하게 된다.
    * */
    // 14줄에서 발생한 에러를 여기로 가지고 와 처리.
    // 우선 순위도 잘 생각해야 함. 다 가져올 수도..
    @ExceptionHandler(NullPointerException.class)
    public String nullHandler(Model model, NullPointerException e) {

        model.addAttribute("key", e.getMessage());

        return "error/nullPointer";
    }

    @GetMapping("userexception")
    public String userException() throws MemberNotFoundException {  // Handler 에게 throws 함.
        boolean check = true;
        if (check) {
            throw new MemberNotFoundException("어디감..");
        }

        return "/";     // 여기에 도달하진 않음.
    }

    @ExceptionHandler(MemberNotFoundException.class)    // throws 된걸 여기서 잡음.
    public String userException(Model model, MemberNotFoundException e){

        model.addAttribute("key", e);

        return "error/memberNotFound";
    }



}
