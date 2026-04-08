package com.wanted.associationmapping.section01.manytoone;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Entity(name = "menu_and_category")
@Table(name = "tbl_menu")
public class Menu {

    @Id
    @Column(name = "menu_code")
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // menu는 autoincrement라서 이 구문있으면 manyToOneInsert 때 에러남.
    private int menuCode;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_price")
    private int menuPrice;

    // 이 구조는 SQL 친화적인 구조이며, JPA 객체 관점에서는 잘못 된 구조이다. (8번이라는 힌트로 카테고리 한 행을 알아가야 함.)
    // private int categoryCode;

    /* comment.
    *   [영속성 전이]
    *   - 특정 엔티티를 영속화(PC 등록) 할 때,
    *   - 연관관계에 있는 엔티티도 같이 영속화 한다는 의미이다.
    *   - 지금 상황에서는 1개의 메뉴를 영속화 할 때,
    *   - 그 메뉴에 포함된 Category 도 같이 영속화를 한다는 의미이다.
    * */
    @ManyToOne(cascade = CascadeType.PERSIST)   // (cascade ...) 안쓰면 manyToOneInsert 실행 시 에러 남. 영속성 전이 때문에.
    @JoinColumn(name = "category_code")
    private Category category;

    @Column(name = "orderable_status")
    private String orderableStatus;

}
