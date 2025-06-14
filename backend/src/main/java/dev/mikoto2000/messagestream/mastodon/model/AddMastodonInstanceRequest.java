package dev.mikoto2000.messagestream.mastodon.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AddInstanceRequest
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddMastodonInstanceRequest {
  private String instanceUrl;
  private String accessToken;
}


