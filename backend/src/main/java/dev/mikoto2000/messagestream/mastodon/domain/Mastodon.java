package dev.mikoto2000.messagestream.mastodon.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.mastodon4j.core.MyMastodonClient;
import org.mastodon4j.core.api.MastodonApi;
import org.mastodon4j.core.api.Timelines;
import org.mastodon4j.core.api.entities.AccessToken;
import org.mastodon4j.core.api.entities.Status;

/**
 * Mastodon
 */
public class Mastodon {

  private MastodonApi api;
  private final String url;

  /**
   * Constructor
   */
  public Mastodon(
      String url,
      String accessToken) {
    this.url = url;
    api = MyMastodonClient.create(url, AccessToken.create(accessToken));
  }

  public List<Message> getHomeTimeline() {
    Timelines timelines = api.timelines();
    List<Status> homeTimeline = timelines.home();

    List<Message> returnValue = new ArrayList<>();
    for (Status status : homeTimeline) {
      var text = Jsoup.parse(status.content()).text();
      if (text != null && !text.isEmpty()) {
        List<String> thumbnailUrls = new ArrayList<>();
        List<String> imageUrls = new ArrayList<>();
        for (var media : status.media_attachments()) {
          if ("image".equals(media.type())) {
            thumbnailUrls.add(media.previewUrl());
            imageUrls.add(media.url());
          }
        }
        String link = status.url().toString();
        String serviceName = String.format("Mastodon - %s", this.url);
        String iconUrl = status.account().avatar();
        returnValue.add(new Message(
            serviceName,
            status.account().display_name(),
            iconUrl,
            text,
            status.created_at().toInstant(),
            link,
            thumbnailUrls,
            imageUrls));
      }
    }

    return returnValue;
  }

  public List<Message> getPublicTimeline() {
    Timelines timelines = api.timelines();
    List<Status> publicTimeline = timelines.pub();

    Set<Message> returnValue = new HashSet<>();
    for (Status status : publicTimeline) {
      var text = Jsoup.parse(status.content()).text();
      if (text != null && !text.isEmpty()) {
        List<String> thumbnailUrls = new ArrayList<>();
        List<String> imageUrls = new ArrayList<>();
        for (var media : status.media_attachments()) {
          if ("image".equals(media.type())) {
            thumbnailUrls.add(media.previewUrl());
            imageUrls.add(media.url());
          }
        }
        String link = status.url().toString();
        String serviceName = String.format("Mastodon - %s", this.url);
        String iconUrl = status.account().avatar();
        returnValue.add(new Message(
            serviceName,
            status.account().display_name(),
            iconUrl,
            text,
            status.created_at().toInstant(),
            link,
            thumbnailUrls,
            imageUrls));
      }
    }

    return new ArrayList<Message>(returnValue);
  }

}
