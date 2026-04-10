package com.wanted.project.domain.menu.model.service;

import com.wanted.project.domain.menu.model.dao.CategoryRepository;
import com.wanted.project.domain.menu.model.dao.MenuRepository;
import com.wanted.project.domain.menu.model.dto.CategoryDTO;
import com.wanted.project.domain.menu.model.dto.MenuDTO;
import com.wanted.project.domain.menu.model.entity.Category;
import com.wanted.project.domain.menu.model.entity.Menu;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;                // 생성자 주입 완료 된 것.
    private final ModelMapper modelMapper;                      // 생성자 주입 완료 된 것.
    private final CategoryRepository categoryRepository;        // 생성자 주입

    /* 1. 메뉴코드로 특정 메뉴 조회하기*/
    public MenuDTO findMenuByMenuCode(int menuCode) {

        // Entity 등장!               // findById = 우리가 만든 것이 아닌 JPA 가 제공하는 것.
        Menu foundMenu = menuRepository.findById(menuCode)
                                       .orElseThrow(IllegalArgumentException::new);
//        System.out.println("foundMenu = " + foundMenu);        // entity를 사용자에게 그대로 보여주면 X. DTO로 보낼 것.

        // map(변환 대상, 변환 할 타입)
        MenuDTO menuDTO = modelMapper.map(foundMenu, MenuDTO.class);

        return menuDTO;     // DTO 타입으로 변환

    }


    public List<MenuDTO> findMenuByPrice(int menuPrice) {

        // Entity 등장!
        List<Menu> menuList = menuRepository.findByMenuPriceGreaterThanOrderByMenuPrice(menuPrice);

        System.out.println("menuList = " + menuList);

        // stream = 하나씩 나열 / map = 변환    | modelMapper.map으로 menu를 dto 타입으로 바꾸고 또 리스트 형태로 바꿈.
        return menuList.stream()
                        .map(menu -> modelMapper.map(menu, MenuDTO.class))
                        .collect(Collectors.toList());
    }

    public List<CategoryDTO> findAllCategory() {

        List<Category> categoryList = categoryRepository.findAllCategory();

        return categoryList.stream()
                           .map(category -> modelMapper.map(category, CategoryDTO.class))
                           .collect(Collectors.toList());

    }

    // 2. 전체 메뉴 조회
    public List<MenuDTO> findMenuAll() {
        List<Menu> menuList = menuRepository.findAll();

        return menuList.stream()
                .map(menu -> modelMapper.map(menu, MenuDTO.class))
                .collect(Collectors.toList());
    }
}


