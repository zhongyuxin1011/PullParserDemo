package com.zyx1011.pullparserdemo;

/**
 * Created by zhongyuxin on 2017/1/28.
 */

public class Book {
    private String id;
    private String author;
    private String title;
    private String price;

    public Book() {
    }

    public Book(String id, String author, String title, String price) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
