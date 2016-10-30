package com.bfwg.security;

import com.bfwg.model.JWTResponse;
import com.bfwg.model.JsonWebToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by fan.jin on 2016-10-19.
 */

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.header}")
    private String tokenHeader;

    public JsonWebToken generateToken() {
        String jws = Jwts.builder()
                .setSubject("fanjin1989@gmail.com")
                .claim("name", "Fan Jin")
                .claim("hasProsche", true)
                .setExpiration(generateExpirationDate())
                .signWith( SignatureAlgorithm.HS512, secret )
                .compact();

        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(jws)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return new JsonWebToken(jws);
    }

    public Boolean validateToken(String token) {
        //final Date expiration = getExpirationDateFromToken(token);
        return !isTokenExpired(token);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    public Claims getClaimsFromToken(String token) {
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

    private Date generateExpirationDate() {
        // second
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

}
