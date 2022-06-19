package com.extrawest.core.dto.auth;

import com.extrawest.core.model.Role;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class SignUpDTO {

    private String name;
    private String surname;
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;
    @Size(min = 8, max = 25)
    private String password;
    private Role role;

}
