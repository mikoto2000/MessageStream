package dev.mikoto2000.messagestream.mastodon.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * MastodonService
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MastodonService {
  @Id
  @GeneratedValue
  private UUID id;
  private UUID accountId;
  private String url;
  private String accessToken;
}
