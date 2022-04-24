package com.ceyhun.bookstore.dao;

import com.ceyhun.bookstore.entity.Book;
import com.ceyhun.bookstore.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookDao extends JpaRepository<Book, Integer> {
    List<Book> findAllByPublisher(User publisher);
    Page<Book> getByNameContainingIgnoreCase(String name, Pageable pageable);
    Optional<Book> findById(int id);
}
