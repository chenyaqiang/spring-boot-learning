package com.springboot.controller;

import com.springboot.model.Book;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.*;

/**
 * @description: 用户创建某本图书    POST	/books/
 * 用户删除对某本图书	DELETE	/books/:id/
 * 用户修改对某本图书	PUT	/books/:id/
 * 用户获取所有的图书 GET /books
 * 用户获取某一图书  GET /books/:id
 * 官方文档：http://swagger.io/docs/specification/api-host-and-base-path/
 * API访问地址：http://localhost:8080/swagger-ui.html
 * @author: wb
 * @data: 2018/1/12 9:30
 * @see:
 * @since:
 */
@RestController
@RequestMapping("books")
public class BookController {

    Map<Long, Book> books = Collections.synchronizedMap(new HashMap<Long, Book>());

    @ApiOperation(value = "获取图书列表", notes = "获取图书列表")
    @GetMapping("")
    public List<Book> getBook() {
        List<Book> bookList = new ArrayList<>(books.values());
        return bookList;
    }

    @ApiOperation(value = "创建图书", notes = "创建图书")
    @ApiImplicitParam(name = "book", value = "图书详细实体", required = true, dataType = "Book")
    @PostMapping("")
    public String postBook(@RequestBody Book book) {
        books.put(book.getId(), book);
        return "success";
    }

    @ApiOperation(value="获图书细信息", notes="根据url的id来获取详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long",paramType = "path")
    @GetMapping("{id}")
    public Book getBook(@PathVariable Long id) {
        return books.get(id);
    }

    @ApiOperation(value="更新信息", notes="根据url的id来指定更新图书信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "图书ID", required = true, dataType = "Long",paramType = "path"),
            @ApiImplicitParam(name = "book", value = "图书实体book", required = true, dataType = "Book")
    })
    @PutMapping("{id}")
    public String putUser(@PathVariable Long id, @RequestBody Book book) {
        Book book1 = books.get(id);
        book1.setName(book.getName());
        book1.setPrice(book.getPrice());
        books.put(id, book1);
        return "success";
    }

    @ApiOperation(value="删除图书", notes="根据url的id来指定删除图书")
    @ApiImplicitParam(name = "id", value = "图书ID", required = true, dataType = "Long",paramType = "path")
    @DeleteMapping("{id}")
    public String deleteUser(@PathVariable Long id) {
        books.remove(id);
        return "success";
    }

    @ApiIgnore//使用该注解忽略这个API
    @GetMapping("hi")
    public String  jsonTest() {
        return " hi you!";
    }
}
