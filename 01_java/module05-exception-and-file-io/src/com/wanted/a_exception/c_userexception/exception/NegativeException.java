package com.wanted.a_exception.c_userexception.exception;

/* comment. 예외처리 클래스 만드는 방법
*   extends Exception
* */
public class NegativeException extends Exception{

    public NegativeException(String msg) {
        super(msg);     // super = 부모 / 부모의 생성자에게 메세지 전달.
    }

}
