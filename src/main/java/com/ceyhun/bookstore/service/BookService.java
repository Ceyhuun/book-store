package com.ceyhun.bookstore.service;

import com.ceyhun.bookstore.dto.BookDetailDto;
import com.ceyhun.bookstore.dto.BookDtoAdd;
import com.ceyhun.bookstore.dto.BookDtoUpdate;
import com.ceyhun.bookstore.entity.Book;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

public interface BookService {
    Book add(BookDtoAdd bookDtoAdd, Principal principal, MultipartFile multipartFile) throws Exception;
    List<BookDetailDto> getAll();
    Book update(Principal principal, BookDtoUpdate bookDtoUpdate, MultipartFile multipartFile) throws Exception;
    List<BookDetailDto> getAll(int pageNo,int pageSize);
    List<BookDetailDto> getAllByName(String name, int pageNo,int pageSize);
    BookDetailDto getById(int id);
    List<BookDetailDto> getAllByPublisherId(int id);
}
