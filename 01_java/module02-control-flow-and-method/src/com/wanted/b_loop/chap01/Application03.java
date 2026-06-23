package com.wanted.b_loop.chap01;

public class Application03 {

    public static void main(String[] args) {

        /* for-each 문
        * 배열이나 컬렉션의 요소를 순회하는데 사용되는 반복문
        * 형식 : for(자료형 변수명 : 배열 혹은 컬렉션) { 실행 코드 }
        * 요소의 갯수를 미리 알 수 없거나, 모든 요소를 순회해야 할 때 사용된다.
        * */

        System.out.println("===회원님의 장바구니 제품 목록===");
        String[] basket = {"닭가슴살", "프로틴", "아르기닌"};

        for (String product : basket){
            System.out.println("product = " + product);
        }


    }

}
