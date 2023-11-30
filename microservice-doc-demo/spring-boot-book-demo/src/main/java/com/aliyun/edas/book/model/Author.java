package com.aliyun.edas.book.model;

import lombok.Data;

/**
 * @author jingfeng.xjf
 * @date 2023/9/5
 */
@Data
public class Author {
    private String name;
    private String email;
    private int age;
    private Book book;
    private IdCard idCard;
}
