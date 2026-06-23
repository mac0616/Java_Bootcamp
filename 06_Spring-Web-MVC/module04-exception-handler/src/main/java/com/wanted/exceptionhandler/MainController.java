package com.wanted.exceptionhandler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    // 원래는 static에 index.html이 있어야 함.
    // 하지만, 시작화면부터 db에서 정보를 가져오는 등 그렇게 사용하고 싶을 때
    // templates 에 main.html 만들어서 사용할 수 있음. 그렇게 사용하기 위해 아래 코드 작성.
    @GetMapping(value = {"/", "/main"})
    public String mainPage() {
        return "main";
    }


}
