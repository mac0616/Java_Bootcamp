package com.wanted.associationmapping.section03.bidirection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class Biservice {

    @Autowired
    private BiRepository repository;

    public Menu findMenu(int menuCode) {
        return repository.findMenu(menuCode);
    }

    /* comment.
    *   oneToMany 관계는 Lazy Loading 이 기본값이다.
    *   따라서 트렌젝션(@Transactional)이 없다고 하면 Menu 가 필요한 상황에서 데이터를 조회하다가 오류가 발생할 수 있다.
    *   트랜젝션 안에서는 변경 된 내용이 자동으로 반영된다 (변경 감지)
    *   여러 객체를 함께 바꿀 때 중간에 실패하면 전부 rollback 을 하는 것이
    *   Transaction의 기능이기 때문에 LazyLoading 시에는 반드시 Transaction 을 작성하자.
    *   (Transaction은 원래 CUD 전용이나 조회(read) 시 readOnly로 사용)
    * */
    @Transactional(readOnly = true)     // transactional 조회 시에는 읽기 전용으로
    public Category findCategory(int categoryCode) {

        Category foundCategory = repository.findCategory(categoryCode); // 이 구문으로 지연로딩 발생

        // 지연로딩 시 menu를 달라!!  -> 이것만 하면 오류. select에 menu 없는데 달라해서. -> (해결)@Transactional 작성
        System.out.println(foundCategory.getMenuList());

        return foundCategory;
    }

    @Transactional
    public void registMenu(Menu newMenu) {
        repository.saveMenu(newMenu);
    }

    @Transactional
    public void registCategory(Category category) {
        repository.saveCategory(category);
    }
}
