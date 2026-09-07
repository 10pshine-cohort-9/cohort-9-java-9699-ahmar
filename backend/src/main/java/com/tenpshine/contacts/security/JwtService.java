package com.tenpshine.contacts.security;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.util.Date;
@Service public class JwtService{
 private final SecretKey key; private final Duration duration;
 public JwtService(@Value("${app.jwt.secret}")String secret,@Value("${app.jwt.expiration-hours:24}")long hours){key=Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));duration=Duration.ofHours(hours);}
 public String create(String email){Instant now=Instant.now();return Jwts.builder().subject(email).issuedAt(Date.from(now)).expiration(Date.from(now.plus(duration))).signWith(key).compact();}
 public String subject(String token){return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();}
}
