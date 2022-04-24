package com.ceyhun.bookstore.mapper;

import com.ceyhun.bookstore.dto.BookDetailDto;
import com.ceyhun.bookstore.dto.BookDtoAdd;
import com.ceyhun.bookstore.dto.BookDtoUpdate;
import com.ceyhun.bookstore.entity.Book;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Primary
@Component("customBookMapperImpl")
@RequiredArgsConstructor
public class BookMapper implements IBookMapper{
    @Override
    public Book addBookDtoToBook(BookDtoAdd bookDtoAdd) {
        Book book =new Book();
        book.setName(bookDtoAdd.getName());
        book.setAuthor(bookDtoAdd.getAuthor());
        book.setPrice(bookDtoAdd.getPrice());
        book.setDescription(bookDtoAdd.getDescription());
        return book;
    }

    @Override
    public List<BookDetailDto> booksToBookDetailsDto(List<Book> books) {
        var list = new ArrayList<BookDetailDto>(books.size());

        for (Book book : books){
            list.add(bookToBookDetailDto(book));
        }
        return list;
    }

    @Override
    public BookDetailDto bookToBookDetailDto(Book book) {
        var publisherName = book.getPublisher().getUsername();

        BookDetailDto bookDetailDto = new BookDetailDto();
        bookDetailDto.setPublisherName(publisherName);
        bookDetailDto.setName(book.getName());
        bookDetailDto.setAuthor(book.getAuthor());
        bookDetailDto.setPrice(book.getPrice());
        bookDetailDto.setImage(book.getImage());
        bookDetailDto.setDescription(book.getDescription());
        return bookDetailDto;
    }

    @Override
    public Book updateBookDtoToBook(BookDtoUpdate bookDtoUpdate) {
        Book book = new Book();
        book.setId(bookDtoUpdate.getId());
        book.setName(bookDtoUpdate.getName());
        book.setAuthor(bookDtoUpdate.getAuthor());
        book.setPrice(bookDtoUpdate.getPrice());
        book.setDescription(bookDtoUpdate.getDescription());
        return book;
    }
}
