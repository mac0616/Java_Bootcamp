package com.wanted.project.domain.menu.model.dao;

import com.wanted.project.domain.menu.model.entity.Category;
import com.wanted.project.domain.menu.model.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// 인터페이스는 구현체아님 껍데기. 그래서 상속 받아야 함.

/* comment.
*   Repository 인터페이스
*   - 데이터 Access 의 추상화
*   - DB 작업의 상세 구현을 숨기고, 어떤 것을 할 지 작성하는 인터페이스
*   - 개발자는 인터페이스의 구현 클래스를 만들지 않으며
*     Spring Data JPA 가 어플리케이션 시작 시 자동으로 구현 객체를 만들어
*     Spring Bean 으로 등록해준다. (자동 구현 - 우리 눈에 보이지 않음.)
*   - JpaRepository<관리할 Entity, 해당 Entity의 @Id 필드 자료형>
* */

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    /* comment. JPA 쿼리메소드임. */
    List<Menu> findByMenuPriceGreaterThanOrderByMenuPrice(int menuPrice);
}
