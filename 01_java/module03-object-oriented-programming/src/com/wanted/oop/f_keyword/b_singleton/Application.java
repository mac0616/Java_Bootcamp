package com.wanted.oop.f_keyword.b_singleton;

public class Application {

    public static void main(String[] args) {

        /* comment. static 키워드를 이용한 singleton 패턴
        *   어플리케이션이 실행될 때 어떤 클래스가 최초 한 번만 메모리에 할당 되고,
        *   그 메모리에 인스턴스를 만들어서 하나의 인스턴스를 공유해 사용하여
        *   메모리 낭비를 방지할 수 있게하는 디자인 패턴이다.
        * */

        /* comment. 싱글톤 패턴을 만드는 방법 2가지
        *   1. 이른 초기화 (Eager) - 초기로딩 느림 / getinstance하면 eager가 빠름.(미리 만들어둬서)
        *   2. 게으른 초기화 (Lazy) - 초기로딩 빠름 / getinstance하면 느림. 실무에서는 다 lazy 사용!!
        * */

//        EagerSingleton eager1 = new EagerSingleton();   private이라 불가능. 메소드 사용해야 함.
        EagerSingleton eager1 = EagerSingleton.getInstance();
        EagerSingleton eager2 = EagerSingleton.getInstance();

        // 두 객체는 static 키워드로 만든 객체이기 때문에 동일한 인스턴스임을 확인할 수 있다.
        System.out.println("eager1 의 주민번호 : " + eager1.hashCode());     // hashcode는 주소 알아보기 쉽게 바꿔줌.
        System.out.println("eager2 의 주민번호 : " + eager2.hashCode());     // eager1 값과 eager2 값은 같음. static이라.

        LazySingleton lazy1 = LazySingleton.getInstance();
        LazySingleton lazy2 = LazySingleton.getInstance();

        // 두 객체는 static 키워드로 만든 객체이기 때문에 동일한 인스턴스임을 확인할 수 있다.
        System.out.println("lazy1 의 주민번호 : " + lazy1.hashCode());
        System.out.println("lazy2 의 주민번호 : " + lazy2.hashCode());
    }

}
