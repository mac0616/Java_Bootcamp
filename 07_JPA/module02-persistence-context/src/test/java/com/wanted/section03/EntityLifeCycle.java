package com.wanted.section03;

import com.wanted.section02.Menu;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    void 준영속_detach_테스트() {

        // given
        // 여기서 .find 할 때 select 문 나옴.
        Menu foundMenu1 = manager.find(Menu.class, 11);     // 영속성 컨테스트한테 11번 메뉴 찾아달라하기
        Menu foundMenu2 = manager.find(Menu.class, 12);     // 영속성 컨테스트한테 12번 메뉴 찾아달라하기

        //when
        manager.detach(foundMenu2);     // 준영속 만들객체 12번 detach(분리)
        foundMenu1.setMenuPrice(5000); // menu1 가격 변경
        foundMenu2.setMenuPrice(5000); // menu1 가격 변경

        //then
        assertEquals(5000, manager.find(Menu.class, 11).getMenuPrice());    // 원래 가격과 manager로 가져온 11번 메뉴 값
//        assertEquals(5000, manager.find(Menu.class, 12).getMenuPrice());
        // 12번은 //when에서 detach해서 관리 대상이 아니기에 영속성 컨텍스트 안에서 모름. 그래서 위에꺼 하면 에럼남.

        // foundMenu1 : update 쿼리문 안했지만 맞게 나옴. 그 이유는 데이터베이스 반영은 commit 후 인데 아직 commit 안했기 때문에.
    }

    @Test
    void 삭제_remove_테스트() {
        /* comment.
        *   remove() : 엔티티를 영속성 컨텍스트 및 DB 에서 삭제한다.
        *   단, 트랜젝션을 제어하지 않으면 영구 반영되지 않는다.
        * */

        Menu foundMenu = manager.find(Menu.class, 2);

        manager.remove(foundMenu);

        Menu refoundMenu = manager.find(Menu.class, 2);

        assertEquals(2, foundMenu.getMenuCode());
        assertEquals(null, refoundMenu);    // 이것도 통과함. (= refoundMenu는 지금 null)
    }

    @Test
    void 병합_merge_수정_테스트() {
        Menu detachMenu = manager.find(Menu.class, 2);
        manager.detach(detachMenu);

        detachMenu.setMenuName("보쌈");
        Menu refoundMenu = manager.find(Menu.class, 2);

        System.out.println("detachMenu.hashCode() = " + detachMenu.hashCode());         // detachMenu.hashCode() = 1231232251
        System.out.println("refoundMenu.hashCode() = " + refoundMenu.hashCode());       // refoundMenu.hashCode() = 220774932

        manager.merge(detachMenu);

        Menu mergeMenu = manager.find(Menu.class, 2);
        System.out.println("mergeMenu.hashCode() = " + mergeMenu.hashCode());           // mergeMenu.hashCode() = 220774932

        assertEquals("보쌈", mergeMenu.getMenuName());

        // 영속성 context 에서는 동일한 것은 하나의 값으로 관리됨.
        // detach를 하면 영속성context 에서 분리되어 관리하지 않는 것이기에 다른 주소 값을 갖는다.
        // (detach 는 완전 버린 것이 아닌 다른 곳에 임시 보관하는 것. merge를 하는 순간 같은 곳을 바라보게 됨.)
        // 우리가 detach를 직접 할일은 없지만 개발하다보면 실수로 됨...
        // 그럼 update, delete, insert 안됨. 그럼 영속성있나 보고.... (그래서 한 번씩  merge를 해줘야 함.)
    }
}
