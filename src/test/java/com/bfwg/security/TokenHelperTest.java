package com.bfwg.security;


import io.jsonwebtoken.ExpiredJwtException;
import org.joda.time.DateTimeUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by fan.jin on 2017-01-08.
 */
public class TokenHelperTest {


    private static final String TEST_USERNAME = "testUser";

    @InjectMocks
    private TokenHelper tokenHelper;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        ReflectionTestUtils.setField(tokenHelper, "EXPIRES_IN", 10L); // 10 sec
        ReflectionTestUtils.setField(tokenHelper, "SECRET", "mySecret");
        DateTimeUtils.setCurrentMillisSystem();
    }

    @Test
    public void testGenerateTokenGeneratesDifferentTokensForDifferentCreationDates() throws Exception {
        final String token = createToken();

        DateTimeUtils.setCurrentMillisFixed(200000L);
        final String laterToken = createToken();

        assertThat(token).isNotEqualTo(laterToken);
    }

    @Test
    public void getUsernameFromToken() throws Exception {
        final String token = createToken();
        assertThat(tokenHelper.getUsernameFromToken(token)).isEqualTo(TEST_USERNAME);
    }

    @Test(expected = ExpiredJwtException.class)
    public void expiredTokenCannotBeRefreshed() throws Exception {
        DateTimeUtils.setCurrentMillisFixed(1L);
        String token = createToken();
        DateTimeUtils.setCurrentMillisSystem();
        assertThat(tokenHelper.canTokenBeRefreshed(token)).isFalse();
    }


    @Test
    public void notExpiredCanBeRefreshed() {
        String token = createToken();
        assertThat(tokenHelper.canTokenBeRefreshed(token)).isTrue();
    }


    private String createToken() {
        return tokenHelper.generateToken(new UserDetailsDummy(TEST_USERNAME).getUsername());
    }

}
