package com.practice2;

import java.util.Scanner;

public class Practice2 {

    public void start() {

        System.out.println("숫자를 한 개 입력하세요 : ");
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();

        if(a <0){
            System.out.println("양수만 입력해주세요.");
        } else if (a %2==0) {
            System.out.println("짝수다");
        } else {
            System.out.println("홀수다");
        }

//        while(true){
//            System.out.println("숫자를 한 개 입력하세요 : ");
//            Scanner sc = new Scanner(System.in);
//            int a = sc.nextInt();
//
//            if(a <0){
//                System.out.println("양수만 입력해주세요.");
//                break;
//            } else if (a %2==0) {
//                System.out.println("짝수다");
//                break;
//            } else {
//                System.out.println("홀수다");
//                break;
//            }
//        }

    }

}
