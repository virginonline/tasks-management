package com.github.virginonline.tasks.web.security;

import com.github.virginonline.tasks.domain.user.Role;
import com.github.virginonline.tasks.domain.user.User;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@UtilityClass
public final class JwtEntityFactory {

  public static JwtEntity create(final User user) {
    return new JwtEntity(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        user.getPassword(),
        mapToGrantedAuthorities(new ArrayList<>(user.getRoles()))
    );
  }

  private static List<GrantedAuthority> mapToGrantedAuthorities(
      final List<Role> roles
  ) {
    return roles.stream()
        .map(Enum::name)
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }

}
