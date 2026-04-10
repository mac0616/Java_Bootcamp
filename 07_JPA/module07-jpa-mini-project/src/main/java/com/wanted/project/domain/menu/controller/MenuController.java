package com.wanted.project.domain.menu.controller;

import com.wanted.project.domain.menu.model.dto.CategoryDTO;
import com.wanted.project.domain.menu.model.dto.MenuDTO;
import com.wanted.project.domain.menu.model.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor    // 필드에 final 키워드가 붙은 친구들을 자동으로 생성자 주입 해줌.
@RequestMapping("/menu/**")
public class MenuController {

    private final MenuService menuService;  // 자동으로 생성자 주입 받음.



    @GetMapping("/{menuCode}")                                  //= 13
    public ModelAndView findByMenuByPathVariable(@PathVariable int menuCode, ModelAndView mv) {

        MenuDTO findMenu = menuService.findMenuByMenuCode(menuCode);

        mv.addObject("result", findMenu);      // 키 값: result,

        mv.setViewName("menu/detail");

        return mv;
    }

    @GetMapping("/querymethod")
    public void queryPage(){}

    @GetMapping("/search")
    public ModelAndView findByMenuPrice(@RequestParam int menuPrice, ModelAndView mv) {

        System.out.println("사용자가 입력한 menuPrice = " + menuPrice);

        List<MenuDTO> menuList = menuService.findMenuByPrice(menuPrice);

        mv.addObject("menuList", menuList);
        mv.addObject("price", menuPrice);
        mv.setViewName("menu/searchResult");
        return mv;
    }

    @GetMapping("/regist")
    public String regist() {
        return "menu/regist";
    }

    // 신규 메뉴 등록 (카테고리 : 사용자가 입력하는 것이 아닌 이미 있는 카테고리 중 선택해야 함.)
    // 해당 메서드는 비동기 방식으로 페이지를 리턴하는 것이 아닌 데이터만 리턴할 것이다.
    @GetMapping("/category")
    /* comment.
    *   @ResponseBody 를 붙이게 되면 웹 페이지에 Json 형태로 데이터를 리턴하게 된다.
    *   Json 은 JavaScript 객체 표기법으로 우리 Java 클래스와 비슷한 역할이라고 보면 된다.
    *   @ResponseBody 는 1개의 페이지에 여러 데이터를 표현할 때 1개의 핸들러메서드에서
    *   여러 데이터를 넣는 것이 아닌 비동기 방식으로 각 핸들러메서드에서 전달되는 값을 조합할 때 유용하게 사용된다.
    * */
    @ResponseBody   // JSON 타입으로 데이터만 리턴.
    // 페이지를 리턴하지 않고 데이터만 리턴할 것.
    public List<CategoryDTO> findCategoryList() {
        return menuService.findAllCategory();
    }


    @GetMapping("/list")
    public ModelAndView findAllMenuList(ModelAndView mv) {
        List<MenuDTO> menuList = menuService.findMenuAll();
        mv.addObject("menuList", menuList);
        mv.setViewName("menu/list");
        return mv;
    }

}
