package com.example.currency.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.currency.jwt.UsernamePasswordAuthRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.stream.Collectors;

@Slf4j
public class CustomAuthFilter extends UsernamePasswordAuthenticationFilter {

/*    public CustomAuthFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        System.out.println("Hello its meeeee3333333");

    }*/

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
/*        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println(username + password);*/
        UsernamePasswordAuthenticationToken authenticationToken;

        try {
            UsernamePasswordAuthRequest usernamePasswordAuthRequest = new ObjectMapper().readValue(request.getInputStream(), UsernamePasswordAuthRequest.class);
            System.out.println("Filter " + usernamePasswordAuthRequest.getUsername() + " " + usernamePasswordAuthRequest.getPassword());
            authenticationToken = new UsernamePasswordAuthenticationToken(usernamePasswordAuthRequest.getUsername() , usernamePasswordAuthRequest.getPassword());
            return this.getAuthenticationManager().authenticate(authenticationToken);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }




/*        String username = request.getParameter("username");
        String password = request.getParameter("password");*/

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("secret123".getBytes());
        String token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(Date.valueOf(LocalDate.now().plusWeeks(2)))
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
        System.out.println("role? " + user.getAuthorities());
        response.addHeader("Authorization", "Bearer " + token);

    }
}
