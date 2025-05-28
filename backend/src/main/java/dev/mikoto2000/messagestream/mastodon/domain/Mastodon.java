package dev.mikoto2000.messagestream.mastodon.domain;

import java.util.ArrayList;
import java.util.List;

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

  /**
   * Constructor
   */
  public Mastodon(
      String url,
      String accessToken) {
    api = MyMastodonClient.create(url, AccessToken.create(accessToken));
  }

  public List<Message> getHomeTimeline() {
    Timelines timelines = api.timelines();
    List<Status> homeTimeline = timelines.home();

    List<Message> returnValue = new ArrayList<>();
    for (Status status : homeTimeline) {
      var text = Jsoup.parse(status.content()).text();
      if (text != null && !text.equals("")) {
        returnValue.add(new Message(
            status.account().display_name(),
            text,
            status.created_at().toInstant()));
      }
    }

    return returnValue;
  }

}
