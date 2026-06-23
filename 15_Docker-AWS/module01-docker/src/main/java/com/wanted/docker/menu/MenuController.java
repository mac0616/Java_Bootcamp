package com.wanted.docker.menu;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MenuController {

    private final MenuRepository menuRepository;

    public MenuController(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    /* HandlerMethod that checks Docker network integration between Spring and MySQL containers. */
    @GetMapping("/menus")
    public List<Menu> findAllMenus() {
        return menuRepository.findAll();
    }

}