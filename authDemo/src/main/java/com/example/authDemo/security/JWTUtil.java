package com.example.authDemo.security;

import com.example.authDemo.models.UserEntity;
import com.example.authDemo.models.tokens.Token;
import com.example.authDemo.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class JWTUtil {

    //generate token after checking
    //validate from actual token
    //get user's email from the token itself

    //@Value("${secret-key}")
    //private static String SECRET_KEY;


    private static long EXPIRE_DURATION = SecurityConstants.JWT_EXPIRATION;
    private static String SECRET_KEY = SecurityConstants.JWT_SECRET;


    @Autowired
    private TokenRepository tokenRepo;


    public static String generateToken(Authentication authentication) {


        //retreive the user object to form the jwt token
        UserEntity user = (UserEntity) authentication.getPrincipal();


        String token  = Jwts.builder()
                    .setSubject(String.format("%s,%s", user.getId(), user.getEmail()))
                    .setIssuer("qf555")
                    .claim("roles", user.getRoles().toString())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                    .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                    .compact();



        return token;


    }

    public String getSubject(String token){

        //get the email and id

        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {

        try {
            Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT was exprired or incorrect",ex.fillInStackTrace());
        }
    }

    public Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }



    //check token valid via the database
    public boolean tokenIsValid(String token){

        boolean result = false;

        Token currToken =tokenRepo.findByToken(token).get();

        if(!currToken.isExpired() && !currToken.isRevoked() ) result = true;

        return  result;

    }




}
