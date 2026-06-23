package com.wanted.associationmapping.section02;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
//@ToString     // // @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY) 쓰려면 지우기
@Entity(name = "section02Menu")
@Table(name = "tbl_menu")
public class Menu {

    @Id
    @Column(name = "menu_code")
   private int menuCode;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_price")
    private int menuPrice;

    @Column(name = "category_code")
    private int categoryCode;   // 반대에서만 메뉴에 대한 정보를 알고 있음. (카테고리 정보를 지움 - 메뉴는 카테고리 정보 모름.)

    @Column(name = "orderable_status")
    private String orderableStatus;

}
