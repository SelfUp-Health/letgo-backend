package com.letgo.auth.domain.service.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.letgo.auth.domain.spi.UserDataService;
import io.jsonwebtoken.*;
import java.util.*;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtService {

  private final UserDataService dataService;

  @Value("${letgo.domain}")
  private String issuer;

  @Value("${jwt.expiration-time:900000}")
  private long expirationTime;

  @Value("${jwt.secret}")
  private String secret;

  public String getTokenFromAuthHeader(String headerValue) {
    return headerValue.split(" ")[1].trim();
  }

  //retrieve username from jwt token
  public String getUsernameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  //retrieve expiration date from jwt token
  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  public String getIssuerFromUntrustedToken(String token) throws JsonProcessingException {
    String[] chunks = token.split("\\.");

    Base64.Decoder urlDecoder = Base64.getUrlDecoder();

    String payload = new String(urlDecoder.decode(chunks[1]));

    Map<String, Object> result = new ObjectMapper().readValue(payload, HashMap.class);
    return result.get("iss").toString();
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  //for retrieving any information from token we will need the secret key
  public Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(secret).build().parseClaimsJws(token).getBody();
  }

  //check if the token has expired
  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  //validate token
  public Boolean validate(String token) {
    final String username = getUsernameFromToken(token);
    var userDetails = dataService.findByUsername(username)
      .orElseThrow(() -> new IllegalArgumentException("User not found"));
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  //generate token for user
  public String generate(String issuer) {
    Map<String, Object> claims = new HashMap<>();
    return generate(issuer, claims, new Date(System.currentTimeMillis() + expirationTime));
  }

  public String generate(String issuer, Map<String, Object> claims) {
    return doGenerateToken(claims, issuer, null);
  }

  public String generate(String issuer, Map<String, Object> claims, Date expiration) {
    return doGenerateToken(claims, issuer, expiration);
  }

  //while creating the token -
  //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
  //2. Sign the JWT using the HS512 algorithm and secret key.
  //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
  //   compaction of the JWT to a URL-safe string
  private String doGenerateToken(Map<String, Object> claims, String subject, Date expiration) {
    return Jwts.builder()
      .claims(claims)
      .subject(subject)
      .issuer(issuer)
      .issuedAt(new Date(System.currentTimeMillis()))
      .expiration(expiration)
      .signWith(SignatureAlgorithm.HS512, secret)
      .compact();
  }
}
