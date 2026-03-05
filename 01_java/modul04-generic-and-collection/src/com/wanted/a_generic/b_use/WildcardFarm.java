package com.wanted.a_generic.b_use;
// 그냥 클래스
public class WildcardFarm {

    /* comment. 와일드카드
     *   제네릭 클래스 타입의 객체를 메소드의 매개변수로 전달 받을 때, 그 객체의 타입 변수를 제한할 수 있다.
     *   <?> : 제한 없음
     *   <? extends Type> : 와일드카드 상한 제한  (위는 안 된다.)
     *   <? super Type> : 와일드카드 하한 제한    (아래는 안 된다.)
     * */

    // 메소드 3개 생성
    public void anyType(RabbitFarm<?> farm) {
        farm.getAnimal().cry();
    }

    public void extendsType(RabbitFarm<? extends Bunny> farm) { // Bunny를 상속받은 것만 나올 수 있도록 함.
        farm.getAnimal().cry();
    }

    public void superType(RabbitFarm<? super Bunny> farm) { // Bunny 위에 있는 Bunny & Rabbit만 사용할 수 있음.
        farm.getAnimal().cry();
    }

}
