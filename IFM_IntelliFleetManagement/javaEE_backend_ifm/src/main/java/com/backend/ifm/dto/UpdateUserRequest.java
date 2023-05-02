package com.backend.ifm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

    private String email;
    private String n_email;
    private String username;
    private String company;
    private String c_password;
    private String n_password;

}
