package com.ceyhun.bookstore.api;

import com.ceyhun.bookstore.service.AuthService;
import com.ceyhun.bookstore.shared.JwtResponse;
import com.ceyhun.bookstore.shared.LoginRequest;
import com.ceyhun.bookstore.shared.MessageResponse;
import com.ceyhun.bookstore.shared.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/user/register")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest){
        authService.registerUser(registerRequest);
        MessageResponse messageResponse = new MessageResponse("User Registered Successfully");
        return ResponseEntity.ok(messageResponse);
    }

    @PostMapping("/publisher/register")
    public ResponseEntity<MessageResponse> registerPublisher(@Valid @RequestBody RegisterRequest registerRequest){
        authService.registerPublisher(registerRequest);
        MessageResponse messageResponse = new MessageResponse("Publisher Registered Successfully");
        return ResponseEntity.ok(messageResponse);
    }
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.login(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }
}
