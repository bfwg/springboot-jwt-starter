package com.bfwg.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.bfwg.common.TimeProvider;
import com.bfwg.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Created by fan.jin on 2016-10-19.
 */

@Component
public class TokenHelper {

	@Value("${app.name}")
	private String APP_NAME;

	@Value("${jwt.secret}")
	public String SECRET;

	@Value("${jwt.expires_in}")
	private int EXPIRES_IN;

	@Value("${jwt.header}")
	private String AUTH_HEADER;

	@Autowired
	TimeProvider timeProvider;

	private MacAlgorithm SIGNATURE_ALGORITHM = Jwts.SIG.HS512;

	public String getUsernameFromToken(String token) {
		String username;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}

	public Date getIssuedAtDateFromToken(String token) {
		Date issueAt;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			issueAt = claims.getIssuedAt();
		} catch (Exception e) {
			issueAt = null;
		}
		return issueAt;
	}

	public String refreshToken(String token) {
		String refreshedToken;
		Date a = timeProvider.now();
		try {
			final Claims claims = getAllClaimsFromToken(token);
			refreshedToken = Jwts.builder()
					.claims(claims)
					.issuedAt(a)
					.expiration(generateExpirationDate())
					.signWith(Keys.hmacShaKeyFor(SECRET.getBytes()), SIGNATURE_ALGORITHM)
					.compact();
		} catch (Exception e) {
			refreshedToken = null;
		}
		return refreshedToken;
	}

	public String generateToken(String username) {
		return Jwts.builder()
				.issuer(APP_NAME)
				.subject(username)
				.issuedAt(timeProvider.now())
				.expiration(generateExpirationDate())
				.signWith(Keys.hmacShaKeyFor(SECRET.getBytes()), SIGNATURE_ALGORITHM)
				.compact();
	}

	private Claims getAllClaimsFromToken(String token) {
		Jws<Claims> claims;
		try {
			claims = Jwts.parser()
					.verifyWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
					.build()
					.parseSignedClaims(token);
		} catch (Exception e) {
			claims = null;
		}
		return claims.getPayload();
	}

	private Date generateExpirationDate() {
		long expiresIn = EXPIRES_IN;
		return new Date(timeProvider.now().getTime() + expiresIn * 1000);
	}

	public int getExpiredIn() {
		return EXPIRES_IN;
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		User user = (User) userDetails;
		final String username = getUsernameFromToken(token);
		final Date created = getIssuedAtDateFromToken(token);
		return (username != null &&
				username.equals(userDetails.getUsername()) &&
				!isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate()));
	}

	private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
		return (lastPasswordReset != null && created.before(lastPasswordReset));
	}

	public String getToken(HttpServletRequest request) {
		/**
		 * Getting the token from Authentication header e.g Bearer your_token
		 */
		String authHeader = getAuthHeaderFromHeader(request);
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7);
		}

		return null;
	}

	public String getAuthHeaderFromHeader(HttpServletRequest request) {
		return request.getHeader(AUTH_HEADER);
	}

}