package com.apploc.applocv1.services.impl;

import com.apploc.applocv1.repository.UserRepository;
import com.apploc.applocv1.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username)  {
                return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }
}
