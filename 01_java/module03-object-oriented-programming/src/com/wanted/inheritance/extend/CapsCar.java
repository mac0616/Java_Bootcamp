package com.wanted.inheritance.extend;

// extends 키워드는 클래스 선언부에 작성한다.
public class CapsCar extends Car {

    public CapsCar(){
        System.out.println("CapsCar 의 기본 생성자 호출됨...");
    }

    // this = 자기자신 / super = 부모 주소.
    @Override
    public void run() {
        System.out.println("경찰차는 삐용삐용하면서 달립니다~~~~~~~~~~~~~~~~~");
    }

    @Override
    public void soundHorn() {
        System.out.println("삐용삐용 경적을 울립니다~~~~~~~~~~~~~다들 비키세요");
    }
}
