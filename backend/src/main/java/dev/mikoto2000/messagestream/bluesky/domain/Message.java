package dev.mikoto2000.messagestream.bluesky.domain;

import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Message class holding service name, poster, icon URL, text, post time, link,
 * and attached thumbnail and full-size image URLs.
 * <p>
 * The nth element in {@code thumbnailUrls} corresponds to the nth element in {@code imageUrls}.
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
  private final List<String> thumbnailUrls;
  private final List<String> imageUrls;
}

