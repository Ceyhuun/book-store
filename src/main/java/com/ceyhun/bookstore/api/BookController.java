package com.ceyhun.bookstore.api;

import com.ceyhun.bookstore.dto.BookDetailDto;
import com.ceyhun.bookstore.dto.BookDtoAdd;
import com.ceyhun.bookstore.dto.BookDtoUpdate;
import com.ceyhun.bookstore.entity.Book;
import com.ceyhun.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping(value = "/add", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasRole('PUBLISHER')")
    public ResponseEntity<Book> add(Principal principal, BookDtoAdd bookDtoAdd, @RequestPart MultipartFile multipartFile) throws Exception {
        var result = bookService.add(bookDtoAdd, principal, multipartFile);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyRole('USER','PUBLISHER')")
    public ResponseEntity<List<BookDetailDto>> getAll() {
        var result = bookService.getAll();
        return ResponseEntity.ok(result);
    }

    @PutMapping(value = "/update", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasRole('PUBLISHER')")
    public ResponseEntity<Book> update(Principal principal, BookDtoUpdate bookDtoUpdate, @RequestPart MultipartFile multipartFile) throws Exception {
        var result = bookService.update(principal, bookDtoUpdate, multipartFile);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAllByPage")
    @PreAuthorize("hasRole('USER') or hasRole('PUBLISHER')")
    public ResponseEntity<List<BookDetailDto>> getAll(@RequestParam int pageNo, @RequestParam int pageSize) {
        var result = bookService.getAll(pageNo - 1, pageSize);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAllByNameAndPage")
    @PreAuthorize("hasRole('USER') or hasRole('PUBLISHER')")
    public ResponseEntity<List<BookDetailDto>> getAllByNameAndPage(@RequestParam String name, @RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        var result = bookService.getAllByName(name, pageNo - 1, pageSize);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAllPublisherId")
    @PreAuthorize("hasRole('USER') or hasRole('PUBLISHER')")
    public ResponseEntity<List<BookDetailDto>> getAllByPublisherId(@RequestParam int id) {
        var result = bookService.getAllByPublisherId(id);
        return ResponseEntity.ok(result);
    }

}

