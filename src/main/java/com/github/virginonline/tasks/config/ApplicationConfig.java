package com.github.virginonline.tasks.config;

import com.github.virginonline.tasks.web.security.JwtTokenFilter;
import com.github.virginonline.tasks.web.security.JwtTokenProvider;
import com.github.virginonline.tasks.web.security.expression.CustomSecurityExceptionHandler;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class ApplicationConfig {

  private final JwtTokenProvider tokenProvider;
  private final ApplicationContext applicationContext;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(
      final AuthenticationConfiguration configuration
  ) throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  public MethodSecurityExpressionHandler expressionHandler() {
    DefaultMethodSecurityExpressionHandler expressionHandler
        = new CustomSecurityExceptionHandler();
    expressionHandler.setApplicationContext(applicationContext);
    return expressionHandler;
  }


  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .addSecurityItem(new SecurityRequirement()
            .addList("bearerAuth"))
        .components(
            new Components()
                .addSecuritySchemes("bearerAuth",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                )
        )
        .info(new Info()
            .title("Task Management System")
            .description("Api docs")
            .version("1.0")
        );
  }

  @Bean
  public SecurityFilterChain filterChain(final HttpSecurity httpSecurity)
      throws Exception {
    httpSecurity
        .csrf(AbstractHttpConfigurer::disable)
        .cors(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .sessionManagement(sessionManagement ->
            sessionManagement
                .sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
        )
        .exceptionHandling(configurer ->
            configurer.authenticationEntryPoint(
                    (request, response, exception) -> {
                      response.setStatus(
                          HttpStatus.UNAUTHORIZED
                              .value()
                      );
                      response.getWriter()
                          .write("Unauthorized.");
                    })
                .accessDeniedHandler(
                    (request, response, exception) -> {
                      response.setStatus(
                          HttpStatus.FORBIDDEN
                              .value()
                      );
                      response.getWriter()
                          .write("Unauthorized.");
                    }))
        .authorizeHttpRequests(configurer ->
            configurer.requestMatchers("/api/v1/auth/**")
                .permitAll()
                .requestMatchers("/swagger-ui/**")
                .permitAll()
                .anyRequest().authenticated())
        .anonymous(AbstractHttpConfigurer::disable)
        .addFilterBefore(new JwtTokenFilter(tokenProvider),
            UsernamePasswordAuthenticationFilter.class);

    return httpSecurity.build();
  }
}
