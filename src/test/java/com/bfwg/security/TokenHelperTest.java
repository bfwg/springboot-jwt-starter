package com.bfwg.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.Date;

import org.assertj.core.util.DateUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import com.bfwg.common.TimeProvider;
import com.bfwg.model.User;

/**
 * Created by fan.jin on 2017-01-08.
 */
public class TokenHelperTest {

	private static final String TEST_USERNAME = "testUser";

	@InjectMocks
	private TokenHelper tokenHelper;

	@Mock
	private TimeProvider timeProviderMock;

	@Before
	public void init() {
		MockitoAnnotations.openMocks(this);

		ReflectionTestUtils.setField(tokenHelper, "EXPIRES_IN", 10); // 10 sec
		ReflectionTestUtils.setField(tokenHelper, "SECRET",
				"Y0[yCzX7Ym;${,[+hj*(E*erX:-%-JU=K!}Sp/m:$gZ.D[EW,fQec3Ha1.rwy)TX");
	}

	@Test
	public void testGenerateTokenGeneratesDifferentTokensForDifferentCreationDates() throws Exception {
		when(timeProviderMock.now())
				.thenReturn(DateUtil.yesterday())
				.thenReturn(DateUtil.now());

		final String token = createToken();
		final String laterToken = createToken();

		assertThat(token).isNotEqualTo(laterToken);
	}

	@Test
	public void tokenShouldExpire() {
		Date beforeSomeTime = new Date(DateUtil.now().getTime() - 20 * 1000);

		when(timeProviderMock.now())
				.thenReturn(beforeSomeTime);

		UserDetails userDetails = mock(User.class);
		when(userDetails.getUsername()).thenReturn(TEST_USERNAME);

		final String mobileToken = createToken();
		assertThat(tokenHelper.validateToken(mobileToken, userDetails)).isFalse();
	}

	@Test
	public void getUsernameFromToken() throws Exception {
		when(timeProviderMock.now()).thenReturn(DateUtil.now());

		final String token = createToken();

		assertThat(tokenHelper.getUsernameFromToken(token)).isEqualTo(TEST_USERNAME);
	}

	@Test
	public void getCreatedDateFromToken() {
		final Date now = DateUtil.now();
		when(timeProviderMock.now()).thenReturn(now);

		final String token = createToken();

		assertThat(tokenHelper.getIssuedAtDateFromToken(token)).isInSameMinuteWindowAs(now);
	}

	@Test
	public void expiredTokenCannotBeRefreshed() {
		when(timeProviderMock.now())
				.thenReturn(DateUtil.yesterday());

		String token = createToken();
		tokenHelper.refreshToken(token);
	}

	@Test
	public void changedPasswordCannotBeRefreshed() throws Exception {
		when(timeProviderMock.now())
				.thenReturn(DateUtil.now());

		User user = mock(User.class);
		when(user.getLastPasswordResetDate()).thenReturn(new Timestamp(DateUtil.tomorrow().getTime()));
		String token = createToken();
		assertThat(tokenHelper.validateToken(token, user)).isFalse();
	}

	@Test
	public void canRefreshToken() throws Exception {
		when(timeProviderMock.now())
				.thenReturn(DateUtil.now())
				.thenReturn(DateUtil.tomorrow());
		String firstToken = createToken();
		String refreshedToken = tokenHelper.refreshToken(firstToken);
		Date firstTokenDate = tokenHelper.getIssuedAtDateFromToken(firstToken);
		Date refreshedTokenDate = tokenHelper.getIssuedAtDateFromToken(refreshedToken);
		assertThat(firstTokenDate).isBefore(refreshedTokenDate);
	}

	private String createToken() {
		return tokenHelper.generateToken(TEST_USERNAME);
	}

}
