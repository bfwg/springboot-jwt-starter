package com.bfwg.security;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Clock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by fan.jin on 2017-01-08.
 */
public class TokenHelperTest {

    private TokenHelper tokenHelper;

    @Before
    public void init() {
        tokenHelper = new TokenHelper();
        ReflectionTestUtils.setField(tokenHelper, "EXPIRES_IN", 1);
        ReflectionTestUtils.setField(tokenHelper, "SECRET", "mySecret");
    }

    @Test(expected=ExpiredJwtException.class)
    public void testGenerateTokenExpired() {
        ReflectionTestUtils.setField(tokenHelper, "currentTimeMillis", System.currentTimeMillis() - 2000);
        String token = tokenHelper.generateToken("fanjin");
        Jwts.parser()
            .setSigningKey("mySecret")
            .parseClaimsJws(token)
            .getBody();
    }
}
