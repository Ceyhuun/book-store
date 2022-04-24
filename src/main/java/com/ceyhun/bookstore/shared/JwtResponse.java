package com.ceyhun.bookstore.shared;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private Integer id;
    private String username;
    private List<String> roles;
}
