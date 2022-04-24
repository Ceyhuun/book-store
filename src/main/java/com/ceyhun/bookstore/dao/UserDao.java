package com.ceyhun.bookstore.dao;

import com.ceyhun.bookstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findById(int id);
    Boolean existsByUsername(String username);
}
