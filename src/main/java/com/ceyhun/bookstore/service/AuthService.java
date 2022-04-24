package com.ceyhun.bookstore.service;

import com.ceyhun.bookstore.entity.User;
import com.ceyhun.bookstore.shared.JwtResponse;
import com.ceyhun.bookstore.shared.LoginRequest;
import com.ceyhun.bookstore.shared.RegisterRequest;

public interface AuthService {
    JwtResponse login(LoginRequest request);
    User registerPublisher(RegisterRequest request);
    User registerUser(RegisterRequest request);
}
