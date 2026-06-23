package com.wanted.oop.f_keyword.a_static;

public class Application {

    public static void main(String[] args) {

        /* comment. static 키워드에 대해 알아보자.*/

        // 객체 생성 구문
        StaticFieldTest st1 = new StaticFieldTest();

        System.out.println("non-static 변수 확인 : " + st1.getNonStaticCount());        // result : 0
        System.out.println("static 변수 확인 : " + st1.getStaticCount());               // result : 0

        // 각 변수를 1씩 증가시키는 구문
        st1.increaseNonStatic();
        st1.increaseStatic();

        System.out.println("non-static 변수 확인 : " + st1.getNonStaticCount());        // result : 1
        System.out.println("static 변수 확인 : " + st1.getStaticCount());               // result : 1

        /* comment. 핵심 포인트
        *   새로운 StaticFieldTest 객체를 생성
        *   sout 구문을 실행했을 때 0, 0이 나오는 것을 기대했지만,
        *   실제로 static 키워드가 붙은 변수는 1이 출력되었다.
        * */
        StaticFieldTest st2 = new StaticFieldTest();
        System.out.println("non-static 변수 확인 : " + st2.getNonStaticCount());        // result : 0
        System.out.println("static 변수 확인 : " + st2.getStaticCount());               // result : 1
        // static은 새로 만들어서 사용했음에도 1로 나옴. 이는 힙 메모리와 전혀 연관이 없음.
        // 즉, 힙 메모리에서 관리하는 것이 아닌 MethodArea에서 관리됨을 알 수 있음.
        // 만약 java와 db를 연결할 때 연결할 길을 만들려고 하는데 매번 만드는 것은 비효율적임.
        // 한 번만 연결하기 위해 사용하는 것이 static임. 즉, static은 값을 공유하기 위해 사용.
        // ex) 에어컨 1개에 리모콘이 30개가 아닌 1개만 필요한 것과 같음.
        // static - 자주 사용 X. 메모리 정리가 안되기 때문에 남발 금지. / 힙은 가비지컬렉터가 청소해줌.

    }

}
