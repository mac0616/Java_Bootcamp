package com.wanted.polymorphism.b_interface;

public class Application {

    public static void main(String[] args) {

//        InterfaceProduct ip = new InterfaceProduct() {}

        // 다형성을 적용해서 실제 구현하고 있는 Product로 객체 생성
        InterfaceProduct ip = new Product();
        ip.methodA();   // 동적 바인딩

        // comment. 인터페이스는 구현시 반드시 활용해야 한다!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


    }

}
