package com.aliyun.edas.book.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author jingfeng.xjf
 * @date 2023/9/5
 */
@Data
public class Book {

    @NotNull
    private String isbn;

    private String title;
    //private Author author;
    //
    //@Range(min = 0, max = 1000)
    //private int pages;
    //private float priceFloat;
    //private double price;
    //private boolean available;
    //private char format;
    //private Integer aInt;
    //private Float aFloat;
    //private Double aDouble;
    //private Boolean aBoolean;
    //private Character character;
    //private List<String> listTest;
    //private String[] stringArrayTest;
    //private int[] intArray;
    //private Integer[] integerArray;
    //private Map<String,String> mapTest;
    //private Set<String> setTest;
    //private List<Author> authors;
    //private Object anyTest;
}
