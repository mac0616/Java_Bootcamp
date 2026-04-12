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
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional   //DML 구문이라 사용
    public int registNewMenu(MenuDTO registMenu) {

        // save 시에는 Entity 타입(Menu.class) 을 넣어야 한다.
        // 하지만 전달받고 있는 객체 타입은 DTO 타입이기 때문에
        // 마찬가지로 modelMapper 로 DTO -> Entity 로 바꿔줄 것이다.

//        두 문장(✅)과 아래 한 문장(☑️)은 같은 의미
        Menu menu = modelMapper.map(registMenu, Menu.class);    //(✅)
        System.out.println("menu = " + menu);
        menuRepository.save(menu);                              //(✅)

//        menuRepository.save(modelMapper.map(registMenu, Menu.class));   (☑️)
        return menu.getMenuCode();

    }

    @Transactional   //DML 구문이라 사용
    public void modifyMenuName(int menuCode, String menuName) {

        // 수정 대상 엔티티 객체 찾아오기
        Menu foundMenu = menuRepository.findById(menuCode).orElseThrow(IllegalArgumentException::new); // 파일이 옵셔널 타입이라 반드시 예외처리 해줘야 함.

        System.out.println("영속성 컨텍스트에서 찾아온 foundMenu = " + foundMenu);
        // foundMenu 변수에는 수정 대상 엔티티가 담겨있다.

        /* 1. setter 사용해서 update -> setter 사용은 지양한다.
        (지양하는 이유 : 엔티티때문 - setter는 public이라 누가 어디서 사용했는지 추적 어려움.) */
//        foundMenu.setMenuName(menuName);

        /* 2. @Builder 어노테이션을 사용한 update  기능 */
//        foundMenu = foundMenu.toBuilder().menuName(menuName).build();       // 영속성 컨텍스트에 반영 안됨. (= 준영속상태)
//        // 새로운 인스턴스가 대입되는 것은 영속성 컨텍스트가 아직 새로운 인스턴스를 알 지 못 하는 상황이다.
//
//        menuRepository.save(foundMenu);     // 영속성 컨텍스트에 반영하기.

        /* 3. Entity 내부에 직접 Builder 패턴을 구현 - 가장 지향❗ */
        foundMenu = foundMenu.changeMenuName(menuName).builder();

        menuRepository.save(foundMenu);

        // 2, 3번은 동일하나 3번처럼 Builder 패턴을 직접 만드는 것을 지향함.
    }

    @Transactional
    public void deleteMenu(int menuCode) {

        menuRepository.deleteById(menuCode);

        System.out.println(menuCode + "번 메뉴 삭제 완료!");

    }
}
