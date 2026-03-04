package com.wanted.oop.d_constructor;

import java.time.LocalDateTime;

public class UserDTO {

    private String id;
    private String pwd;
    private String name;
    private LocalDateTime enrollDate;   // 등록일

    /* comment.
    *   생성자의 사용 목적
    *   1. 객체 생성 시점에 수행 할 명령이 있는 경우에 사용한다.
    *   2. 매개변수가 있는 생성자의 경우 매개변수로 전달 받은 값으로
    *      기본값이 아닌 필드를 초기화 하면서 객체를 생성할 목적으로 사용한다.
    *   3. 작성한 생성자 외에는 인스턴스를 생성하는 방법을 제공하지 않겠다.
    *   */

    /* comment. 생성자 표현식
    *   접근제한자 클래스명([매개변수]) {
    *       객체 생성 시점에 수행할 코드
    *       this. 필드명 = 매개변수
    *   }
    * */

    // 기본 생성자 -> 우리가 작성하지 않아도 컴파일러가 알아서 추가해줌.
    public UserDTO(){
        System.out.println("UserDTO 의 기본 생성자 호출됨"); // Application에 작성한 print문보다 먼저 나옴. new 구문보다 먼저 생성되어 있었다.
    }

    /* comment. 주의!!!
    *   만약 클래스 내부에 매개변수가 있는 생성자를 작성했다면, 기본 생성자는 자동으로 생성되지 않는다.
    *   따라서 이 점을 주의하자.
    *  */

    // 매개변수가 있는 생성자
    // 위에 있는 것과 이름은 UserDTO로 같으나 역할이 다르기 때문에 다른 메소드로 인식함.
    public UserDTO(String id, String pwd, String name, LocalDateTime enrollDate) {
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.enrollDate = enrollDate;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id='" + id + '\'' +
                ", pwd='" + pwd + '\'' +
                ", name='" + name + '\'' +
                ", enrollDate=" + enrollDate +  // 클래스도 참조자료형이므로 기본값 = null
                '}';
    }
}
