package com.wanted.b_collection.a_list.dto;

// DTO는 반드시 기본생성자, 모든 필드를 초기화 하는 생성자, getter&setter, toString 이 4개지가 있어야 함.
public class BookDTO {

    private int no;
    private String title;
    private String author;
    private int price;

    // 기본 생성자
    public BookDTO() {}

    // 모든 필드를 초기화 하는 생성자
    public BookDTO(int no, String title, String author, int price) {
        this.no = no;
        this.title = title;
        this.author = author;
        this.price = price;
    }

    // getter & setter
    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    //
    @Override
    public String toString() {
        return "BookDTO{" +
                "no=" + no +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                '}';
    }
}
