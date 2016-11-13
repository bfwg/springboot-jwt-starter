package com.bfwg.rest;

import com.bfwg.security.SecurityUtility;
import com.bfwg.security.TokenUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by fan.jin on 2016-11-13.
 */

/**
 * This class is for demonstration purpose only and it has nothing to do with business logic
 */
@RestController
public class TokenController {

    @Autowired
    SecurityUtility securityUtility;

    @Autowired
    TokenUtils tokenUtils;


    @RequestMapping( method = GET, value= "/parse-token")
    public ResponseEntity<ParsedToken> parseToken() {
        String token = securityUtility.getToken();
        return new ResponseEntity<>( new ParsedToken(token), HttpStatus.OK );
    }

    // demo parsed token class
    class ParsedToken {

        private JwsHeader header;
        private Claims payload;
        private String signature;

        public ParsedToken(String token) {
            header = tokenUtils.getHeader(token);
            payload = tokenUtils.getClaims(token);
            signature = tokenUtils.getSignature();
        }

        public JwsHeader getHeader() {
            return header;
        }

        public void setHeader(JwsHeader header) {
            this.header = header;
        }

        public Claims getPayload() {
            return payload;
        }

        public void setPayload(Claims payload) {
            this.payload = payload;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }
    }
}


