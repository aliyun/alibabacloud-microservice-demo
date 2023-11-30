package com.aliyun.edas.book.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aliyun.edas.book.model.BaseResult;
import com.aliyun.edas.book.model.Book;
import com.aliyun.edas.book.model.BookRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jingfeng.xjf
 * @date 2023/9/5
 */
@RestController
@RequestMapping(value = {"/book"})
public class BookController {

    @RequestMapping(value = "/getBook")
    public BaseResult<Book> getBook(String isbn) {
        return BaseResult.ok(new Book());
    }

    @PostMapping(value = "/createBookJson", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResult<String> createBookJson(@RequestBody BookRequest book, String testParam) {
        return BaseResult.ok(book.getIsbn());
    }

    @PostMapping(value = "/createBookFormPojo", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public BaseResult<Book> createBookFormPojo(BookRequest book) {
        return BaseResult.ok(new Book());
    }

    @PostMapping(value = "/createBookFormPrivateType", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String createBookFormPrivateType(@RequestParam String param1, @RequestParam Integer param2) {
        return "success";
    }

    @PostMapping(value = "/createBookFormParam", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String createBookFormParam(String param1, Integer param2) {
        return "success";
    }

    @PostMapping(value = "/createBookFormWithoutConsumes")
    public BaseResult<String> createBookFormWithoutConsumes(Book book) {
        return BaseResult.ok(book.getIsbn());
    }

    @GetMapping("/getBookPojo")
    public BaseResult<Book> getBookPojo(Book book) {
        return BaseResult.ok(new Book());
    }

    @GetMapping(path = "/getBookPath/{isbn}")
    public BaseResult<Book> getBookPath(@PathVariable("isbn") String isbn) {
        return BaseResult.ok(new Book());
    }

    @GetMapping(path = "/getBookHeader")
    public BaseResult<Book> getBookHeader(@RequestHeader("RequestId") String requestId) {
        return BaseResult.ok(new Book());
    }

    @DeleteMapping("/deleteBook")
    public BaseResult<Boolean> deleteBook(String isbn) {
        return BaseResult.ok(true);
    }

    @GetMapping("/listBooks")
    public BaseResult<List<Book>> listBooks() {
        return BaseResult.ok(Collections.emptyList());
    }

    @GetMapping("/listBooksMap")
    public BaseResult<Map<String, Book>> listBooksMap() {
        return BaseResult.ok(Collections.emptyMap());
    }

    @GetMapping("/testMapObj")
    public Map<String, Book> testMap() {
        return Collections.emptyMap();
    }

    @GetMapping("/testMapString")
    public Map<String, String> testMapString() {
        return Collections.emptyMap();
    }

    public static final String TEST_LITERAL = "test_literal";

    @PostMapping(value = "/testRequestParamValue", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public BaseResult<String> testRequestParamValue(
        @RequestParam(name = "TestParam", required = false) String testParam,
        @RequestAttribute(name = "TestAttribute", required = false) String testAttribute,
        @RequestParam(name = TEST_LITERAL) String test_literal123
    ) {
        return BaseResult.ok("success");
    }

    @PostMapping(value = "/testInnerClass", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> testInnerClass(
        HttpServletRequest request, HttpServletResponse response, HttpSession session
    ) {
        return new ResponseEntity<>("hello", HttpStatus.ACCEPTED);
    }

    @PostMapping(value = {"/testMultiPath1", "testMultiPath"}, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String testMultiPath() {
        return "success";
    }

}
