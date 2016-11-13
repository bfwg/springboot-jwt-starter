package com.bfwg.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by fan.jin on 2016-10-19.
 */

@Component
public class TokenUtils {

    @Value("${app.name}")
    private String APP_NAME;

    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.expires_in}")
    private Long EXPIRES_IN;

    private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    public String generateToken(String username) {
        String jws = Jwts.builder()
                .setIssuer( APP_NAME )
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate())
                .signWith( SIGNATURE_ALGORITHM, SECRET )
                .compact();
        return jws;
    }

    public JwsHeader getHeader(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getHeader();
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    public String getSignature() {
        return SIGNATURE_ALGORITHM.getJcaName().toUpperCase()
                + "( base64UrlEncode(header) + \".\" + base64UrlEncode(payload), " + SECRET + " )";
    }


    public Boolean isTokenExpired(String token) {
        final Date expiration = getClaims(token).getExpiration();
        return expiration.before(generateCurrentDate());
    }

    private Date generateCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + this.EXPIRES_IN * 1000);
    }
}
