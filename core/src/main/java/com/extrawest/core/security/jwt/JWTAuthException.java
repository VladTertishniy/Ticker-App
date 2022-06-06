package com.extrawest.core.security.jwt;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

@Getter
public class JWTAuthException extends AuthenticationException {
    private HttpStatus httpStatus;

    public JWTAuthException(String msg) {
        super(msg);
    }

    public JWTAuthException(String msg, HttpStatus httpStatus) {
        super(msg);
        this.httpStatus = httpStatus;
    }
}
