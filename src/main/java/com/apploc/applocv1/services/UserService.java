package com.apploc.applocv1.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;


public interface UserService {
    UserDetailsService userDetailsService();
}
