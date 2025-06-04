package dev.mikoto2000.messagestream.bluesky.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BlueskyService
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlueskyService {
  @Id
  @GeneratedValue
  private UUID id;
  private UUID accountId;
  private String url;
  private String handle;
  private String appPassword;
  private String accessToken;
  private String refreshToken;
  private LocalDateTime tokenExpiresAt;
}
