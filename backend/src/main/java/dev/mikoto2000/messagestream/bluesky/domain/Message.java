package dev.mikoto2000.messagestream.bluesky.domain;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Message class holding service name, poster, icon URL, text, post time, and link.
 */
@Getter
@ToString
@AllArgsConstructor
public class Message {
  private final String serviceName;
  private final String poster;
  private final String iconUrl;
  private final String text;
  private final Instant postedAt;
  private final String link;
}

