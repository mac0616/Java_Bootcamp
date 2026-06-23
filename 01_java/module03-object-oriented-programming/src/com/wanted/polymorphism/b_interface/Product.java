package com.wanted.polymorphism.b_interface;

public class Product implements InterfaceProduct {
    // 인터페이스는 구현한 메소드는 반드시 사용해야 함.

    @Override
    public void methodA() {
        System.out.println("methodA 호출됨..");
    }

}
