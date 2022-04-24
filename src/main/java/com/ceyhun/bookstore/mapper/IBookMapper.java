package com.ceyhun.bookstore.mapper;

import com.ceyhun.bookstore.dto.BookDetailDto;
import com.ceyhun.bookstore.dto.BookDtoAdd;
import com.ceyhun.bookstore.dto.BookDtoUpdate;
import com.ceyhun.bookstore.entity.Book;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface IBookMapper {
    Book addBookDtoToBook(BookDtoAdd bookDtoAdd);
    List<BookDetailDto> booksToBookDetailsDto(List<Book> books);
    BookDetailDto bookToBookDetailDto(Book book);
    Book updateBookDtoToBook(BookDtoUpdate bookDtoUpdate);
}
