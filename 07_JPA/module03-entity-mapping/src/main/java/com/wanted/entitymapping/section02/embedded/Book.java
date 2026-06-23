package com.wanted.entitymapping.section02.embedded;

import jakarta.persistence.*;


import java.time.LocalDate;

@Entity
@Table(name = "tbl_book")
public class Book {

    @Id
    @Column(name = "book_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookNo;

    @Column(name = "book_title")
    private String bookTitle;

    @Column(name = "author")
    private String author;  // 저자

    @Column(name = "pulisher")
    private String pulisher; // 출판사

    @Column(name = "created_date")
    private LocalDate createdDate; // 출판일

    @Embedded
    private Price price;
    // '출판 가격, 할인율, 할인 적용 가격' 이 원래라면 더 있어야 하지만 이 3가지 컬럼을 하나로 묶을 것.

    public Book() {}

    public Book(String bookTitle, String author, String pulisher, LocalDate createdDate, Price price) {
        this.bookTitle = bookTitle;
        this.author = author;
        this.pulisher = pulisher;
        this.createdDate = createdDate;
        this.price = price;
    }

}
