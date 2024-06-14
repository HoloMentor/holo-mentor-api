package com.holomentor.holomentor.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
@NoArgsConstructor
public class User {
    private Serial id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private String created_at;
}
