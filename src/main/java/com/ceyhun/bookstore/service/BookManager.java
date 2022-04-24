package com.ceyhun.bookstore.service;

import com.ceyhun.bookstore.dao.BookDao;
import com.ceyhun.bookstore.dao.RoleDao;
import com.ceyhun.bookstore.dao.UserDao;
import com.ceyhun.bookstore.dto.BookDetailDto;
import com.ceyhun.bookstore.dto.BookDtoAdd;
import com.ceyhun.bookstore.dto.BookDtoUpdate;
import com.ceyhun.bookstore.entity.Book;
import com.ceyhun.bookstore.entity.ERole;
import com.ceyhun.bookstore.entity.Role;
import com.ceyhun.bookstore.entity.User;
import com.ceyhun.bookstore.exception.BookException;
import com.ceyhun.bookstore.exception.PublisherException;
import com.ceyhun.bookstore.exception.UserException;
import com.ceyhun.bookstore.mapper.BookMapper;
import com.ceyhun.bookstore.shared.RoleMessages;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookManager implements BookService {
    private final BookDao bookDao;
    private final UserDao userDao;
    private final RoleDao roleDao;
    private final BookMapper mapper;

    @Override
    public Book add(BookDtoAdd bookDtoAdd, Principal principal, MultipartFile multipartFile) throws Exception {
        User publisher = userDao.findByUsername(principal.getName()).orElseThrow(() ->
                new UsernameNotFoundException("Username not found with name " + principal.getName()));
        Book book = mapper.addBookDtoToBook(bookDtoAdd);
        book.setImage(FileUploader.upload(multipartFile));
        book.setPublisher(publisher);
        bookDao.save(book);
        return book;
    }

    @Override
    public List<BookDetailDto> getAll() {
        var books = bookDao.findAll();
        return mapper.booksToBookDetailsDto(books);
    }

    @Override
    public Book update(Principal principal, BookDtoUpdate bookDtoUpdate, MultipartFile multipartFile) throws Exception {
        User publisher = userDao.findByUsername(principal.getName()).orElseThrow(() ->
                new UsernameNotFoundException("Username not found with name" + principal.getName()));
        Book book = bookDao.findById(bookDtoUpdate.getId())
                .orElseThrow(() -> new BookException(RoleMessages.BOOK_NOT_FOUND));
        if (!publisher.getBooks().contains(book)) {
            throw new BookException(RoleMessages.BOOK_NOT_FOUND);
        }
        book.setImage(FileUploader.upload(multipartFile));
        book.setPublisher(publisher);
        bookDao.save(book);
        return book;
    }

    @Override
    public List<BookDetailDto> getAll(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        var books = bookDao.findAll(pageable).getContent();
        return mapper.booksToBookDetailsDto(books);
    }

    @Override
    public List<BookDetailDto> getAllByName(String name, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        var books = bookDao.getByNameContainingIgnoreCase(name, pageable).getContent();
        return mapper.booksToBookDetailsDto(books);
    }

    @Override
    public BookDetailDto getById(int id) {
        var book = bookDao.findById(id).orElseThrow(()->new BookException(RoleMessages.ROLE_NOT_FOUND));
        return mapper.bookToBookDetailDto(book);
    }

    @Override
    public List<BookDetailDto> getAllByPublisherId(int id) {

        var publisher = userDao.findById(id)
                .orElseThrow(()->new UserException(RoleMessages.USER_NOT_FOUND));

        Role rolePublisher = roleDao.findByName(ERole.ROLE_PUBLISHER)
                .orElse(Role.builder().name(ERole.ROLE_PUBLISHER).build());

        var roles = publisher.getRoles();
        if (!roles.contains(rolePublisher)){
            throw new PublisherException(RoleMessages.PUBLISHER_NOT_FOUND);
        }

        var books = bookDao.findAllByPublisher(publisher);
        return mapper.booksToBookDetailsDto(books);
    }
}
