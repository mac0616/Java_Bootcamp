package com.wanted.associationmapping.section01.manytoone;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ManyToOneRepository {

    @PersistenceContext
    private EntityManager manager;

    public Menu find(int menuCode){
        return manager.find(Menu.class, menuCode);
    }

    /* comment. JPQL
    *   JPA 가 작성해주는 SQL 은 전부를 가져옴.
    *   JPQL을 사용하면 원하는 것만 가져 올 수 있음. 단, FROM 절도 ENTITY 로 작성해야 함.
    * */
    public String findCategoryName(int menuCode) {

        String jpql = "SELECT c.categoryName " +
                      "FROM menu_and_category m " +
                      "JOIN m.category c " +
                      "WHERE m.menuCode = :menuCode";

        return manager.createQuery(jpql, String.class)
                      .setParameter("menuCode", menuCode)
                      .getSingleResult();         // 결과는 1개다.

    }

    public void regist(Menu menu) {
        manager.persist(menu);
    }
}
