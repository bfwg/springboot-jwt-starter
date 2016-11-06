package com.bfwg.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fan.jin on 2016-10-19.
 */

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(UserDetails userDetails) {
        // Put userDetails into token
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("authority", userDetails.getAuthorities());

        String jws = Jwts.builder()
                .setIssuer( "springboot-jwt-demo" )
//                .setClaims( claims )
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith( SignatureAlgorithm.HS512, secret )
                .compact();
        return jws;
    }

    public Claims getClaims(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getClaims(token).getExpiration();
        return expiration.before(new Date());
    }

}
