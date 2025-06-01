package dev.mikoto2000.messagestream.configuration;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for CORS settings.
 */
@ConfigurationProperties(prefix = "messagestream.cors")
public class CorsProperties {

  /** The list of allowed origins. */
  private List<String> allowedOrigins = List.of();

  /** The list of allowed origin patterns (supports wildcards). */
  private List<String> allowedOriginPatterns = List.of();

  /** The list of allowed HTTP methods. */
  private List<String> allowedMethods = List.of();

  /** The list of allowed HTTP headers. */
  private List<String> allowedHeaders = List.of();

  /** Whether user credentials are supported. */
  private Boolean allowCredentials = Boolean.FALSE;

  /** The path pattern to apply the CORS configuration to. */
  private String mappingPath = "/**";

  public List<String> getAllowedOrigins() {
    return allowedOrigins;
  }

  public void setAllowedOrigins(List<String> allowedOrigins) {
    this.allowedOrigins = allowedOrigins;
  }

  public List<String> getAllowedOriginPatterns() {
    return allowedOriginPatterns;
  }

  public void setAllowedOriginPatterns(List<String> allowedOriginPatterns) {
    this.allowedOriginPatterns = allowedOriginPatterns;
  }

  public List<String> getAllowedMethods() {
    return allowedMethods;
  }

  public void setAllowedMethods(List<String> allowedMethods) {
    this.allowedMethods = allowedMethods;
  }

  public List<String> getAllowedHeaders() {
    return allowedHeaders;
  }

  public void setAllowedHeaders(List<String> allowedHeaders) {
    this.allowedHeaders = allowedHeaders;
  }

  public Boolean getAllowCredentials() {
    return allowCredentials;
  }

  public void setAllowCredentials(Boolean allowCredentials) {
    this.allowCredentials = allowCredentials;
  }

  public String getMappingPath() {
    return mappingPath;
  }

  public void setMappingPath(String mappingPath) {
    this.mappingPath = mappingPath;
  }
}