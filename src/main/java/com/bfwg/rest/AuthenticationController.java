package com.bfwg.rest;

import com.bfwg.model.JWTResponse;
import com.bfwg.model.JsonWebToken;
import com.bfwg.security.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Date;

/**
 * Created by fan.jin on 2016-10-16.
 */

@RestController
public class AuthenticationController {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @RequestMapping("/login")
    public ResponseEntity<JsonWebToken> testBuild() {
        return new ResponseEntity<>( jwtTokenUtil.generateToken(), HttpStatus.OK );
    }

    @RequestMapping("/test-parse")
    public Claims testParse(HttpServletRequest request) {
        String jwt = request.getHeader(tokenHeader);
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        System.out.println(jwt);
        System.out.println(claims);
        return claims;
    }
}
