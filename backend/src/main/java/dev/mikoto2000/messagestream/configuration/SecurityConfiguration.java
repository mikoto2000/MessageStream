package dev.mikoto2000.messagestream.configuration;

import java.util.List;
import java.util.Objects;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import dev.mikoto2000.messagestream.configuration.CorsProperties;

/**
 * SecurityConfiguration
 */
@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(CorsProperties.class)
public class SecurityConfiguration {

  /**
   * 認可のための SecurityFilterChain を設定
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(authorize -> authorize
            // swagger-ui と swagger の yaml 定義関連は権限不要
            .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/**").permitAll()
            .anyRequest().authenticated())
        // セッションをステートレスモードにする
        // ステートレスな Web API として実装するならステートはいらない
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        // SPA の CSRF 対策として、クッキーに CSRF トークンを保存し、 JS から読めるようにしておく
        .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
        // CORS の設定を追加
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        // リソースサーバーで、 JWT を使用する
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt
            .jwtAuthenticationConverter(jwtAuthenticationConverter())));

    return http.build();
  }

  private final CorsProperties corsProperties;

  public SecurityConfiguration(CorsProperties corsProperties) {
    this.corsProperties = Objects.requireNonNull(corsProperties);
  }

  /**
   * CORS の設定
   */
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    var configuration = new CorsConfiguration();

    if (!corsProperties.getAllowedOrigins().isEmpty()) {
      configuration.setAllowedOrigins(corsProperties.getAllowedOrigins());
    }
    if (!corsProperties.getAllowedOriginPatterns().isEmpty()) {
      configuration.setAllowedOriginPatterns(corsProperties.getAllowedOriginPatterns());
    }
    if (!corsProperties.getAllowedMethods().isEmpty()) {
      configuration.setAllowedMethods(corsProperties.getAllowedMethods());
    }
    if (!corsProperties.getAllowedHeaders().isEmpty()) {
      configuration.setAllowedHeaders(corsProperties.getAllowedHeaders());
    }
    configuration.setAllowCredentials(corsProperties.getAllowCredentials());

    var source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration(corsProperties.getMappingPath(), configuration);
    return source;
  }

  @Bean
  public JwtAuthenticationConverter jwtAuthenticationConverter() {
    JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
    jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");

    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

    return jwtAuthenticationConverter;
  }
}
