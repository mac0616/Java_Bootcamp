package com.wanted.b_fileio;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Application02 {

    public static void main(String[] args) {

        /* comment. try() */
        Scanner sc = new Scanner(System.in);
        System.out.println("저장할 메세지 입력하주세요 : ");
        String msg = sc.nextLine();

        /* comment.
        *   try() 구문은 try-cath 구문을 발전시킨 구문이며,
        *   finally 블럭에서 사용한 자원을 해제하는 것이 필수였지만,
        *   try() 를 사용하게 되면, 사용한 리소스를 finally 블럭 없이
        *   자동으로 제거해준다.
        *  */
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("user_input"))) {
            writer.write("사용자 입력 : " + msg);
            writer.write("작성 시간 : " + java.time.LocalDateTime.now());
            writer.flush();
            System.out.println("사용자의 입력 데이터 저장 완료!!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
