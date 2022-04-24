package com.ceyhun.bookstore.service;

import com.ceyhun.bookstore.dao.RoleDao;
import com.ceyhun.bookstore.dao.UserDao;
import com.ceyhun.bookstore.entity.ERole;
import com.ceyhun.bookstore.entity.Role;
import com.ceyhun.bookstore.entity.User;
import com.ceyhun.bookstore.exception.UsernameException;
import com.ceyhun.bookstore.security.jwt.JwtUtils;
import com.ceyhun.bookstore.security.service.UserDetailsImpl;
import com.ceyhun.bookstore.shared.JwtResponse;
import com.ceyhun.bookstore.shared.LoginRequest;
import com.ceyhun.bookstore.shared.RegisterRequest;
import com.ceyhun.bookstore.shared.RoleMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthManager implements AuthService{

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserDao userDao;
    private final RoleDao roleDao;
    private final PasswordEncoder passwordEncoder;

    @Override
    public JwtResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        JwtResponse response = new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles);

        return response;
    }

    @Override
    public User registerPublisher(RegisterRequest request) {
        if (userDao.existsByUsername(request.getUsername())){
            throw new UsernameException(RoleMessages.DUPLICATE_USERNAME);
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        Role rolePublisher = roleDao.findByName(ERole.ROLE_PUBLISHER)
                .orElse(Role.builder().name(ERole.ROLE_PUBLISHER).build());

        Set<Role> roles = new HashSet<>();
        roles.add(rolePublisher);
        roleDao.saveAll(roles);
        user.setRoles(roles);
        return userDao.save(user);
    }

    @Override
    public User registerUser(RegisterRequest request) {
        if (userDao.existsByUsername(request.getUsername())){
            throw new UsernameException(RoleMessages.DUPLICATE_USERNAME);
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        Role roleUser = roleDao.findByName(ERole.ROLE_USER)
                .orElse(Role.builder().name(ERole.ROLE_USER).build());

        Set<Role> roles = new HashSet<>();
        roles.add(roleUser);
        roleDao.saveAll(roles);
        user.setRoles(roles);
        return userDao.save(user);
    }
}
