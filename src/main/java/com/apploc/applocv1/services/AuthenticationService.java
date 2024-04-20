package com.apploc.applocv1.services;

import com.apploc.applocv1.dto.JwtAuthenticationResponse;
import com.apploc.applocv1.dto.RefreshTokenRequest;
import com.apploc.applocv1.dto.SigninRequest;
import com.apploc.applocv1.dto.SignupRequest;
import com.apploc.applocv1.entities.User;

public interface AuthenticationService {

    User signup(SignupRequest signupRequest);

    JwtAuthenticationResponse signin(SigninRequest signinRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
