package com.example.currency.jwt;

import antlr.StringUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class JwtTokenVerifier extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        System.out.println("token " + token);
        if (token == null || !token.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        try{
            token = token.replace("Bearer ", "");
            Algorithm algorithm = Algorithm.HMAC256("secret123".getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();

            DecodedJWT decodedJWT = verifier.verify(token);

            String username = decodedJWT.getSubject();

            String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            Arrays.stream(roles).forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role));
            });
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }catch (Exception e){
            response.setHeader("error222", e.getMessage());
            response.sendError(HttpStatus.FORBIDDEN.value());
        }
        filterChain.doFilter(request, response);


    }
}
