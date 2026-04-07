package com.wanted.section03;

import com.wanted.section02.Menu;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

public class EntityLifeCycle {

    private static EntityManagerFactory factory;
    // 애플리케이션 당 딱 하나만 생성하며, DB 연동을 위한 전체 설정을 가지고 있는 공장
    private EntityManager manager;
    // DB에 저장하고 조회하는 실제 작업을 수행하며, 하나의 작업(세션)이 끝나면 닫아줘야 합니다.
    // 작업 하나당 매니저 한 개

    @BeforeAll
    static void initFactory(){
        factory = Persistence.createEntityManagerFactory("jpatest");
    }

    @BeforeEach
    void initManager(){
        manager = factory.createEntityManager();
    }

    @AfterEach
    void closeManager(){
        manager.close();
    }

    @AfterAll
    static void closeFactory(){
        factory.close();
    }

    @Test
    void 비영속_상태_테스트(){

        /* hi.
        *   객체를 생성하면(new), 영속성 컨텍스트와는 전혀 관련 없는 비영속 상태이다.
        *  */

        // given
        Menu foundMenu = manager.find(Menu.class, 1);
        Menu newMenu = new Menu();
        newMenu.setMenuCode(foundMenu.getMenuCode());
        newMenu.setMenuName(foundMenu.getMenuName());
        newMenu.setMenuPrice(foundMenu.getMenuPrice());
        newMenu.setCategoryCode(foundMenu.getCategoryCode());
        newMenu.setOrderableStatus(foundMenu.getOrderableStatus());
        // 자료형 같고, 값도 똑같음.

        // when
        boolean isTrue = (foundMenu == newMenu);

        Assertions.assertFalse(isTrue);

    }

    @Test
    void 영속성_테스트_메서드(){

        /* hi.
         *   객체를 생성하면(new), 영속성 컨텍스트와는 전혀 관련 없는 비영속 상태이다.
         *  */

        // given
        Menu foundMenu = manager.find(Menu.class, 1);
        Menu newMenu = manager.find(Menu.class, 1);

        // when
        boolean isTrue = (foundMenu == newMenu);

        // then
        Assertions.assertFalse(isTrue); // assertTrue / assertFalse

    }

}
