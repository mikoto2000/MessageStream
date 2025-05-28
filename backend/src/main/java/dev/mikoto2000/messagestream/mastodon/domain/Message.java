package dev.mikoto2000.messagestream.mastodon.domain;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Message class holding poster, text, and post time for Mastodon.
 */
@Getter
@ToString
@AllArgsConstructor
public class Message {
  private final String poster;
  private final String text;
  private final Instant postedAt;
}

