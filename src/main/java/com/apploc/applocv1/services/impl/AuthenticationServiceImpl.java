package com.apploc.applocv1.services.impl;

import com.apploc.applocv1.dto.JwtAuthenticationResponse;
import com.apploc.applocv1.dto.RefreshTokenRequest;
import com.apploc.applocv1.dto.SigninRequest;
import com.apploc.applocv1.dto.SignupRequest;
import com.apploc.applocv1.entities.Role;
import com.apploc.applocv1.entities.User;
import com.apploc.applocv1.repository.UserRepository;
import com.apploc.applocv1.services.AuthenticationService;
import com.apploc.applocv1.services.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public User signup(SignupRequest signupRequest){
        User user = new User();

        user.setUsername(signupRequest.getUsername());
        user.setNom(signupRequest.getNom());
        user.setPrenom(signupRequest.getPrenom());
        user.setEmail(signupRequest.getEmail());
        user.setAdresse(signupRequest.getAdresse());
        user.setNumTel(signupRequest.getNumTel());
        user.setRole(Role.User);
        user.setPwd(passwordEncoder.encode(signupRequest.getPassword()));

        return userRepository.save(user);


    }

    public JwtAuthenticationResponse signin(SigninRequest signinRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getUsername(), signinRequest.getPassword()));

        var user = userRepository.findByUsername(signinRequest.getUsername()).orElseThrow(() -> new IllegalArgumentException("Username ou mot de passe invalide "));
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);

        return jwtAuthenticationResponse;

    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        String userName = jwtService.extractUserName(refreshTokenRequest.getToken());
        var user = userRepository.findByUsername(userName).orElseThrow();
        if (jwtService.isTokenValid(refreshTokenRequest.getToken(),user)){
            var jwt = jwtService.generateToken(user);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());

            return jwtAuthenticationResponse;

        }

        return null;
    }
}
