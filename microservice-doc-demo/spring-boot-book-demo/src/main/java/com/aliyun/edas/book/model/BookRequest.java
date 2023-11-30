package com.aliyun.edas.book.model;

import javax.validation.constraints.NotNull;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

/**
 * @author jingfeng.xjf
 * @date 2023/9/26
 */
@Data
public class BookRequest {

    @NotNull
    private String isbn;

    private String title;
    private AuthorRequest author;

    @Range(min = 0, max = 1000)
    private int pages;
    private float priceFloat;
    private double price;
    private boolean available;
    private char format;
    private Integer aInt;
    private Float aFloat;
    private Double aDouble;
    private Boolean aBoolean;
    private Character character;

}
