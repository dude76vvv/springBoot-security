package com.example.authDemo.security;

import com.example.authDemo.models.Role;
import com.example.authDemo.models.UserEntity;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil;

    private boolean hasAuthorizationBearer(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")) {
            return false;
        }

        return true;
    }

    private String getAccessToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = header.split(" ")[1].trim();
        return token;
    }

    private void setAuthenticationContext(String token, HttpServletRequest request) {
        UserDetails userDetails = getUserDetails(token);

        UsernamePasswordAuthenticationToken
                authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private UserDetails getUserDetails(String token) {

        //construct userDetails needed to create UsernamePasswordAuthenticationToken from jwt
        //UsernamePasswordAuthenticationToken to be use set SecurityContextHolder

        //alternative get from UserDetailService which use userRepo.findByEmail
        //the role need to be SimpleGrantedAuthority type using  new SimpleGrantedAuthority(role.getName())

        UserEntity userDetails = new UserEntity();

        Claims claims = jwtUtil.parseClaims(token);


        String subject = (String) claims.get(Claims.SUBJECT);
        String roles = (String) claims.get("roles");

        //list string to string then split into array
        roles = roles.replace("[", "").replace("]", "");
        String[] roleNames = roles.split(",");

        for (String aRoleName : roleNames) {
            userDetails.addRole(new Role(aRoleName));
        }

        String[] jwtSubject = subject.split(",");

        userDetails.setId(Integer.parseInt(jwtSubject[0]));
        userDetails.setEmail(jwtSubject[1]);

        return userDetails;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

//      1st level of check, inspecting the header
        if (!hasAuthorizationBearer(request)) {
            filterChain.doFilter(request, response);
            return;
        }

//      get actual token
        String token = getAccessToken(request);

//      validate the token using jt
        if (!jwtUtil.validateToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

//      go to controller after update SecurityContextHolder
        setAuthenticationContext(token, request);
        filterChain.doFilter(request, response);
    }
}
