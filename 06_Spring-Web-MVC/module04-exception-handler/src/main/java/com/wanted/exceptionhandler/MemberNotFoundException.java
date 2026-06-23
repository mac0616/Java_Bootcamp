package com.wanted.exceptionhandler;

// 예외 관련 클래스가 됨.
public class MemberNotFoundException extends Exception{

    public MemberNotFoundException(String message){
        super(message);     // super = 부모.
    }

}
