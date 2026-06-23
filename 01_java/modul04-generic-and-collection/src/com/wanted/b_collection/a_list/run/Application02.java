package com.wanted.b_collection.a_list.run;

import com.wanted.b_collection.a_list.compare.AscendingPrice;
import com.wanted.b_collection.a_list.compare.DescendingPrice;
import com.wanted.b_collection.a_list.compare.TitleSort;
import com.wanted.b_collection.a_list.compare.TitleSort2;
import com.wanted.b_collection.a_list.dto.BookDTO;

import java.util.ArrayList;
import java.util.List;

public class Application02 {

    public static void main(String[] args) {

        /* comment. ArrayList 활용하기! */
        // 여러 권의 책을 하나의 변수로 관리하고 싶다.

        List<BookDTO> bookList = new ArrayList();

        bookList.add(new BookDTO(1, "홍길동전", "허균", 50000));
        bookList.add(new BookDTO(2, "목민심서", "정약용", 30000));
        bookList.add(new BookDTO(3, "동의보감", "허준", 40000));
        bookList.add(new BookDTO(4, "삼국사기", "김부식", 45000));
        bookList.add(new BookDTO(5, "삼국유사", "일연", 58000));
        System.out.println("bookList = " + bookList);


        for (BookDTO book : bookList){
            System.out.println("book = " + book);
        }

        // 가격 순으로 오름차순 정렬
        // compare 패키지 만들고 그 안에 정렬 가능한 클래스 상속 받아 구현 예정
        // 가격 오름차순 클래스(AscendingPrice) 작성 완료

        bookList.sort(new AscendingPrice());
        System.out.println("\n============================오름차순 정렬 후============================");
        for (BookDTO book : bookList){
            System.out.println("book = " + book);
        }

        // 가격 오름차순 정렬은 같이 해보았다.
        // 그러면 이제 어떤 식으로 코드들이 상호작용 하는 지 확인을 했으니.
        // 가격 내림차순, 제목 정렬(String 클래스에 compareTo() 메소드 활용) 등을 시도해보자.
        bookList.sort(new DescendingPrice());
        System.out.println("\n============================내림차순 정렬 후============================");
        for (BookDTO book : bookList){
            System.out.println("book = " + book);
        }

        bookList.sort(new TitleSort());
        System.out.println("\n============================제목 오름차순 정렬 후============================");
        for (BookDTO book : bookList){
            System.out.println("book = " + book);
        }

        bookList.sort(new TitleSort2());
        System.out.println("\n============================제목 내림차순 정렬 후============================");
        for (BookDTO book : bookList){
            System.out.println("book = " + book);
        }
    }

}
