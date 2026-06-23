package com.wanted.a_generic.a_basic;

public class Application {

    public static void main(String[] args) {

        /* comment. Generic 이란?
        *   제네릭은 데이터의 타입을 일반화 한다는 의미이다.
        *   클래스나 메소드에서 사용할 내부 데이터 타입을 컴파일 시에 지정하는 방법을 의미한다.
        *   컴파일 시점에 미리 타입에 대한 검사를 진행하여, 클래스나 메소드 내부에서 사용되는 객체의 타입 안정성을 높일 수 있다.
        *
        * */
        // 에러는 런타임 시점(코드 올림)보다 컴파일 시점(코드 작성중)에 나오는게 좋음. 런타임 시 뜬다면... 디버깅해야함..

        GenericTest gt = new GenericTest();  // =GenericTest<> gt = new GenericTest(); 와 같은 의미. 다 넣겠다.
        gt.setValue(1);
        System.out.println("gt = " + gt);
        // gt = com.wanted.a_generic.a_basic.GenericTest@119d7047 (객체여서 주소나옴)
        // 이걸 해결하기 위해 GenericTest에서 toString 추가해줌. -> gt = GenericTest{value=1}

        gt.setValue("문자열");
        System.out.println("gt = " + gt);   // gt = GenericTest{value=문자열}
        System.out.println("========================================");
        // 문자열, int 다 들어감. 지금은 좋지만 여러 개발자들이 사용하려할 때 gt에는 어떤 형을 넣어야 하는지 헷갈림.

        /* comment.
        *   <> 다이아몬드 연산자 내부의 자료형은 기본자료형이 들어갈 수 없다.
        *   그러면 도대체 int , char , boolean (기본자료형) 이런 값을 저장하고 싶다면 어떻게 해야 하나?
        *   - Wrapper 클래스 가 등장하게 된다.
        *   기본자료형 -> Wrapper 클래스
        *     int -> Integer
        *     String -> String
        *     byte -> Byte
        *     short -> Short
        *     boolean -> Boolean
        *     char -> Character
        *  */
        GenericTest<Integer> gt1 = new GenericTest<Integer>();
//        gt1.setValue("문자열");
        gt1.setValue(1);
        System.out.println("gt1 = " + gt1);

        GenericTest<String> gt2 = new GenericTest<String>();
        gt2.setValue("문자열");
        System.out.println("gt2 = " + gt2);

    }

}
