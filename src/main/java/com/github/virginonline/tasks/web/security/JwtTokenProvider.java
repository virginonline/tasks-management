package com.github.virginonline.tasks.web.security;

import com.github.virginonline.tasks.domain.exception.AccessDeniedException;
import com.github.virginonline.tasks.domain.user.Role;
import com.github.virginonline.tasks.domain.user.User;
import com.github.virginonline.tasks.service.UserService;
import com.github.virginonline.tasks.service.props.JwtProperties;
import com.github.virginonline.tasks.web.dto.auth.JwtResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {
  private final JwtProperties jwtProperties;

  private final UserDetailsService userDetailsService;
  private final UserService userService;
  private Key key;

  @PostConstruct
  public void init() {
    this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
  }

  public String createAccessToken(final Long userId,
      final String username,
      final Set<Role> roles) {
    Claims claims = Jwts.claims().setSubject(username);
    claims.put("id", userId);
    claims.put("roles", resolveRoles(roles));
    Instant validity = Instant.now()
        .plus(jwtProperties.getAccess(), ChronoUnit.HOURS);
    return Jwts.builder()
        .setClaims(claims)
        .setExpiration(Date.from(validity))
        .signWith(key)
        .compact();
  }

  private List<String> resolveRoles(final Set<Role> roles) {
    return roles.stream()
        .map(Enum::name)
        .collect(Collectors.toList());
  }

  public String createRefreshToken(final Long userId, final String username) {
    Claims claims = Jwts.claims().setSubject(username);
    claims.put("id", userId);
    Instant validity = Instant.now()
        .plus(jwtProperties.getRefresh(), ChronoUnit.DAYS);
    return Jwts.builder()
        .setClaims(claims)
        .setExpiration(Date.from(validity))
        .signWith(key)
        .compact();
  }

  public JwtResponse refreshUserTokens(final String refreshToken) {
    JwtResponse jwtResponse = new JwtResponse();
    if (!validateToken(refreshToken)) {
      throw new AccessDeniedException();
    }
    Long userId = Long.valueOf(getId(refreshToken));
    User user = userService.getById(userId);
    jwtResponse.setId(userId);
    jwtResponse.setUsername(user.getUsername());
    jwtResponse.setAccessToken(
        createAccessToken(userId, user.getUsername(), user.getRoles())
    );
    jwtResponse.setRefreshToken(
        createRefreshToken(userId, user.getUsername())
    );
    return jwtResponse;
  }

  public boolean validateToken(final String token) {
    Jws<Claims> claims = Jwts
        .parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token);
    return !claims.getBody().getExpiration().before(new Date());
  }

  private String getId(final String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .get("id")
        .toString();
  }

  private String getUsername(final String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }
  private String getEmail(final String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .get("email")
        .toString();
  }

  public Authentication getAuthentication(final String token) {
    String username = getUsername(token);
    UserDetails userDetails
        = userDetailsService.loadUserByUsername(username);
    return new UsernamePasswordAuthenticationToken(userDetails,
        "",
        userDetails.getAuthorities());
  }
}
