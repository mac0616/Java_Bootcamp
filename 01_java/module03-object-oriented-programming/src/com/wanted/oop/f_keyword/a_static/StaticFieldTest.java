package com.wanted.oop.f_keyword.a_static;

public class StaticFieldTest {

    private int nonStaticCount;
    private static int staticCount;

    // 기본 생성자
    public StaticFieldTest() {}

    public int getNonStaticCount() {
        return nonStaticCount;
    }

    public static int getStaticCount() {
        return staticCount;
    }

    /* 각 필드를 호출 시 마다 1씩 증가시키는 용도의 메소드 */
    public void increaseNonStatic() {
        this.nonStaticCount++;
    }

    // this는 클래스 자기자신. 주소를 의미
    // static이 붙은 키워드는 힙 메모리 공간에 없음. MethodArea (Static)영역에 있음. 그래서 this.해도 나오지 않는 것.
    public void increaseStatic() {
//        this.
        // 클래스명.변수명
        StaticFieldTest.staticCount++;
    }
}
