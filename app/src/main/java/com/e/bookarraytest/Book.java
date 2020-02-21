package com.e.bookarraytest;

import java.util.List;

public class Book {
    private String title; // 학생이름
    private String author;  // 학부모 예명
    private String isbn;    // 전화번호
    private String category_name; // 학생구분
    private String uid;  // user ID
    //private List<String> hrteacher;

    public Book() {
    }



    public Book(String title, String author, String isbn, String category_name,String uid) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.category_name = category_name;
        this.uid = uid;
      //  this.hrteacher;



    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    //public void setHrteacher(List<String> hrteacher) {
    //    this.hrteacher = hrteacher;
    //}

    //public String getHrteacher() {
    //    return hrteacher;
    //}

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
