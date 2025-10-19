package com.jayesh.bookmyshow.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret.key}")
    private String jwtSecretKey;

    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        System.out.println("bearerToken: " + bearerToken);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public SecretKey getKey(){
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(UserDetails userDetails) {
        String username = userDetails.getUsername();
        String token = Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*5))
                .signWith(getKey())
                .compact();
        System.out.println("Generated Token: " + token);
        return token;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload().getSubject();
    }

    public boolean validateJwtToken(String token)
    {
        try{
            Jwts.parser().verifyWith( getKey() ).build().parseSignedClaims(token);
            return true;
        }
        catch (ExpiredJwtException e)
        {
            System.out.println("Token Expired "+e.getMessage());
            return false;
        }
        catch (SignatureException e)
        {
            System.out.println("Invalid JWT Signature "+e.getMessage());
            return false;
        }
        catch (Exception e)
        {
            System.out.println("JWT exception "+e.getMessage());
            return false;
        }
    }

}
