package com.wanted.elasticbeanstalk;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuController {

    private final MenuRepository repository;

    public MenuController(MenuRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/health")
    public String check() {
        return "Cloud 배포 Test V_1.0.0";
    }

    @GetMapping("/menus/{menuCode}")
    public Menu findMenuByMenuCode(@PathVariable int menuCode) {

        return repository.findByMenuCode(menuCode);

    }
}
