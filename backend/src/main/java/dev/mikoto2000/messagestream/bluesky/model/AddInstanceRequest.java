package dev.mikoto2000.messagestream.bluesky.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AddInstanceRequest
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddInstanceRequest {
  private String instanceUrl;
  private String handle;
  private String password;
}

