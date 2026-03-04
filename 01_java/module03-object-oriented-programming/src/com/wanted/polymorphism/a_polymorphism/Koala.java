package com.wanted.polymorphism.a_polymorphism;

public class Koala extends Animal {

    @Override
    public void eat() {
        System.out.println("코알라가 유칼리투스 잎을 먹습니다.");
    }

    @Override
    public void run() {
        System.out.println("코알라가 다른 나무로 폴짝 뛰어갑니다.");
    }

    @Override
    public void bark() {
        System.out.println("코알코알");
    }

    public void sleep() {
        System.out.println("코알라는 하루에 20시간을 숙면합니다..zzz");
    }

}
