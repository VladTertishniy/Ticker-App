package com.extrawest.core.configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
class AuthInterceptor implements RequestInterceptor {
    @Value("${remote.secret.key}")
    private String secretKey;

    private static final String REQUEST_HEADER_VALUE = "secretKey";

    @Override
    public void apply(RequestTemplate template) {
        template.header(REQUEST_HEADER_VALUE, secretKey);
    }
}
