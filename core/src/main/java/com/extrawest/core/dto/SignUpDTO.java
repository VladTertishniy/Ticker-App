package com.extrawest.core.dto;

import com.extrawest.core.model.Role;
import lombok.Data;

@Data
public class SignUpDTO {

    private String name;
    private String surname;
    private String email;
    private String password;
    private Role role;

}
