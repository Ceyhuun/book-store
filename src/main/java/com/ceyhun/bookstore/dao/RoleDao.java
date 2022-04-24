package com.ceyhun.bookstore.dao;

import com.ceyhun.bookstore.entity.ERole;
import com.ceyhun.bookstore.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleDao extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
    Boolean existsByName(ERole name);
}
