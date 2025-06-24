package com.product.trial.backend.config;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthFilter implements Filter {

    private static final String SECRET_KEY = "IraNivtPdCc2kRa7QsLWZ+9n0vDPY2dgbcAWuU8NDOA=";

    @Override
    public void doFilter(  
            jakarta.servlet.ServletRequest request,
            jakarta.servlet.ServletResponse response,
            FilterChain chain) throws IOException, ServletException { // methode de validation du token et role

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authHeader = httpRequest.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); 

            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(SECRET_KEY)
                        .parseClaimsJws(token)
                        .getBody();

                String username = claims.getSubject();
                String role = (String) claims.get("role");

                
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        new User(username, "", Collections.singleton(() -> "ROLE_" + role.toUpperCase())),
                        null,
                        Collections.singleton(() -> "ROLE_" + role.toUpperCase())
                );

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
