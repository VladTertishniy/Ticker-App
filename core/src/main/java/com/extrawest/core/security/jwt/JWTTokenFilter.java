package com.extrawest.core.security.jwt;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
public class JWTTokenFilter extends GenericFilterBean {
    private final JWTTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest reg, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) reg);
        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (JWTAuthException e) {
            SecurityContextHolder.clearContext();
            ((HttpServletResponse) res).sendError(e.getHttpStatus().value());
            throw new JWTAuthException("Invalid token");
        }
        filterChain.doFilter(reg, res);
    }
}
