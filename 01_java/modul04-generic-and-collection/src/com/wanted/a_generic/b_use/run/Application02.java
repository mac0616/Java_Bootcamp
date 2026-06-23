package com.wanted.a_generic.b_use.run;

import com.wanted.a_generic.b_use.*;

public class Application02 {

    public static void main(String[] args) {

        /* comment. 와일드카드
         *   제네릭 클래스 타입의 객체를 메소드의 매개변수로 전달 받을 때,
         *   그 객체의 타입 변수를 제한할 수 있다.
         *   <?> : 제한 없음
         *   <? extends Type> : 와일드카드 상한 제한  (위는 안 된다.)
         *   <? super Type> : 와일드카드 하한 제한    (아래는 안 된다.)
         * */

        /* 코드 읽는 법..
        * Application2 -> main 먼저 보기 -> WildcardFarm wfarm = new WildcardFarm(); 구문으로 WildcardFarm이란 공간 만듦.
        * -> new하면 WildcardFarm 안 3가지 메소드 사용 준비 완(anyType, extendsType, superType)
        * -> wfarm.anyType(new RabbitFarm<Rabbit>(new Rabbit()));  에서 1번은 new Rabbit(). 토끼(Rabbit) 생김
        *                                                              2번 new RabbitFarm<Rabbit>(..) 농장에 토끼 넣음
        * -> WildcardFarm 클래스에서 (RabbitFarm<?> farm) 을 농장 안에 들어간 토끼랑 퉁침.
        * -> farm에서 . 찍는 순간 레빗으로 들어가는거임.
        * -> 레빗 안에는 크라이 있음. 크라이 불러옴.
        * */

        WildcardFarm wfarm = new WildcardFarm();

        /*
        Rabbit rabbit = new Rabbit();
        RabbitFarm<Rabbit> rfarm = new RabbitFarm<>(rabbit);
        wfarm.anyType(rfarm);
        => 위 3줄 밑에 한 줄 같은 의미
        wfarm.anyType(new RabbitFarm<Rabbit>(new Rabbit()));
         */

        wfarm.anyType(new RabbitFarm<Rabbit>(new Rabbit()));        // 오->왼 으로 읽기 소괄호 우선이라 : 새 Rabbit을 만들어서 RabbitFarm에 Rabbit 넣기.
        wfarm.anyType(new RabbitFarm<Bunny>(new Bunny()));
        wfarm.anyType(new RabbitFarm<DrunkenBunny>(new DrunkenBunny()));

        System.out.println("=============================================");
//        wfarm.extendsType(new RabbitFarm<Rabbit>(new Rabbit()));    // Bunny를 상속받은 것만 나올 수 있도록 함.
        wfarm.extendsType(new RabbitFarm<Bunny>(new Bunny()));
        wfarm.extendsType(new RabbitFarm<DrunkenBunny>(new DrunkenBunny()));

        System.out.println("=============================================");
//        wfarm.superType(new RabbitFarm<Rabbit>(new Rabbit()));      // Bunny 위에 있는 Bunny & Rabbit만 사용할 수 있음.
        wfarm.superType(new RabbitFarm<Bunny>(new Bunny()));
//        wfarm.superType(new RabbitFarm<DrunkenBunny>(new DrunkenBunny()));

    }

}
