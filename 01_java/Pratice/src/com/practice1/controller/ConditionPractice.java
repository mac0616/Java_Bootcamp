package com.practice1.controller;

import java.util.Scanner;

public class ConditionPractice {

    public void start() {

        System.out.println("1. 수정\n2. 조회\n3. 삭제\n4. 종료\n");
        System.out.println("메뉴 번호를 입력하세요 : ");
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();

        if (input == 1) {
            System.out.println("수정 메뉴입니다.");
        } else if (input == 2) {
            System.out.println("조회 메뉴입니다.");
        } else if (input == 3) {
            System.out.println("삭제 메뉴입니다.");
        } else {
            System.out.println("종료 메뉴입니다.");
        }

//        while(true){
//            System.out.println("1. 수정\n2. 조회\n3. 삭제\n4. 종료\n");
//            System.out.println("메뉴 번호를 입력하세요 : ");
//            Scanner sc = new Scanner(System.in);
//            int input = sc.nextInt();
//
//            if(input == 1){
//                System.out.println("수정 메뉴입니다.");
//                break;
//            } else if(input==2){
//                System.out.println("조회 메뉴입니다.");
//                break;
//            } else if(input == 3){
//                System.out.println("삭제 메뉴입니다.");
//                break;
//            } else {
//                System.out.println("종료 메뉴입니다.");
//                break;
//            }
//        }

    }

}