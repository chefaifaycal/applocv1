package com.apploc.applocv1.dto;

import lombok.Data;

@Data
public class SignupRequest {

    private String nom;
    private String prenom;
    private String username;
    private String email;
    private String NumTel;
    private String adresse;
    private String password;

}
