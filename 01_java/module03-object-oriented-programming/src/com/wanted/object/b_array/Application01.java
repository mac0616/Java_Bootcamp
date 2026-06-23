package com.wanted.object.b_array;

public class Application01 {

    public static void main(String[] args) {

        /* comment. 배열이란?
        *   동일한 자료형의 묶음이다.
        *   여기서 핵심 키포인트는 동일한 자료형!!
        *   배열은 heap 영역에 생성이 되며, new 연산자를 통해 할당한다.
        *   new 키워드는 할당 연산자. 이렇게 부를거임.
        * */

        /* comment.
        *   배열의 사용이유
        *   1. 만약 배열을 사용하지 않는다면, 변수를 여러 개 만들어야 한다.
        *   2. 각각의 변수는 규칙이 없기 때문에 반복문을 통한 연속 처리가 불가능하다.
        *   ---
        *   배열의 사용 방법
        *   1. 자료형[] 변수명;     ,     자료형 변수명[]      //선언
        * (ex. int[] x;- 이 방법 사용)    (ex. int y[]; - 이런 방법이 있구나 알기)
        *   2. new int[크기];  ,  { 값들 };     // 할당
        * */

        int[] iarr = new int[5];
        System.out.println("iarr = " + iarr);   // 주소공간
        System.out.println("iarr의 길이 = " + iarr.length);    // 여기서 length는 변수라 값 입력 안함. / .length() = 메소드

        // 배열에 들어있는 값에 접근하는 방법
        System.out.println(iarr[0]);

        /* comment. 중요!! heap 공간은 값이 비어있을 수 없다.
        *   그러나 우리는 값을 넣은 적이 없는데 0이라는 값이 출력되었다.
        *   그렇다는 것은 각 자료형 별 기본값으로 세팅이 된다 라는 것이다.
        *   ----
        *   정수 : 0
        *   실수 : 0.0
        *   논리 : false
        *   문자 : 유니코드
        *   참조형 : null
        * */

        // 10개의 정수가 들어갈 수 있는 배열 생성
        int[] iarr2 = new int[10];  // 정수형

        // 초기값을 0 세팅하는 이유는? 배열은 0 시작인 인덱스 번호 체계를 갖는다.
        for (int i = 0; i < iarr2.length; i++){
            System.out.println("iarr2[" + i + "] 공간의 값 : " + iarr2[i]);
        }

        String[] sarr = new String[5];  // 참조자료형
        for (int i = 0; i < sarr.length; i++){
            System.out.println("sarr[" + i + "] 공간의 값 : " + sarr[i]);
        }

        // 배열의 값에 접근하는 방식 : 변수명[공간 번호(인덱스)]
        System.out.println("NullPointerException 발생 : " + sarr[4].length());


    }

}
