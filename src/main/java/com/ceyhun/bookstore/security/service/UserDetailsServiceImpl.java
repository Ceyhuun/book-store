package com.ceyhun.bookstore.security.service;

import com.ceyhun.bookstore.dao.UserDao;
import com.ceyhun.bookstore.entity.User;
import com.ceyhun.bookstore.shared.RoleMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException(RoleMessages.BAD_CREDENTIAL));
        return UserDetailsImpl.build(user);
    }
}
