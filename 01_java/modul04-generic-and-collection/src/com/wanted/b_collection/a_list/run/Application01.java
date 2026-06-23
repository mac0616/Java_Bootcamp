package com.wanted.b_collection.a_list.run;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Application01 {

    public static void main(String[] args) {

        /* comment. Collection Framework
        *   1. List
        *   - 순서 있는 데이터 집합으로 데이터의 중복을 허용한다.
        *   2. Set
        *   - 순서 없는 데이터 집합으로 데이터의 중복을 허용하지 않는다.
        *   3. Map
        *   - 키와 값 한 쌍으로 이루어지는 데이터 집합이다.
        *   - key 는 Set 방식으로 관리가 되어 있기 때문에 중복이 허용되지 않는다.
        *  */

        // List는 인터페이스라 아래처럼 사용 불가. 이미 상속 받아 만들어진거 불러와서 사용. 그게 ArrayList
//        List list = new List();

        /* comment.
        *   ArrayList 는 가장 많이 사용되는 컬렉션의 클래스이다.
        *   내부적으로 배열의 특징을 가지고 있으며, 배열의 특징이기 때문에
        *   인덱스를 이용해 각 공간의 값에 접근할 수 있다. */
        List list = new ArrayList();            // list는 힙 메모리에 있음.

        list.add("apple");
        list.add("apple");      // 중복 값 허용 O.
        list.add(123);
        list.add(123.123);
        list.add(new Date());

        System.out.println("list = " + list);   // 근데 주소값 안나오고 값이 나옴. 이미 내부적으로 toString()되어 있기 때문임.

        System.out.println("list 의 사이즈 " + list.size());    // size 정해주지 않아도 알아서 관리함.

        for (int i=0; i < list.size(); i++) {
            // List 안에 있는 값 꺼내는 방법
            System.out.println(i + " : " + list.get(i));

        }
        // .add했을 때 2가지가 나옴. 이게 오버로딩
        list.add(1, "banana");  // 1번 자리에 '바나나' 넣기
        System.out.println("list = " + list);

        list.remove(2);                 // 2번 자리 ['apple'] 지우기
        System.out.println("list = " + list);

        System.out.println("=====================================");

        // 제네릭 개념을 사용해서 String 값만 들어가는 List 생성
        List<String> stringList = new ArrayList<>();
        stringList.add("a");
        stringList.add("c");
        stringList.add("d");
        stringList.add("b");
        System.out.println("stringList = " + stringList);
        Collections.sort(stringList);       // sort = 정렬
        System.out.println("stringList = " + stringList);

    }

}
