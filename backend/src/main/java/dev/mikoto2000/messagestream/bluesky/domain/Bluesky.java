package dev.mikoto2000.messagestream.bluesky.domain;

import java.util.ArrayList;
import java.util.List;
import java.time.Instant;

import work.socialhub.kbsky.BlueskyFactory;
import work.socialhub.kbsky.api.app.bsky.FeedResource;
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedGetTimelineRequest;
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedGetTimelineResponse;
import work.socialhub.kbsky.api.entity.com.atproto.server.ServerCreateSessionRequest;
import work.socialhub.kbsky.api.entity.share.Response;
import work.socialhub.kbsky.auth.BearerTokenAuthProvider;
import work.socialhub.kbsky.model.app.bsky.feed.FeedDefsFeedViewPost;
import work.socialhub.kbsky.model.app.bsky.feed.FeedPost;

import dev.mikoto2000.messagestream.bluesky.domain.Message;

/**
 * Bluesky
 */
public class Bluesky {

  private final work.socialhub.kbsky.Bluesky bsky;
  private final BearerTokenAuthProvider auth;

  public Bluesky(
      String url,
      String identifier,
      String password) {

    bsky = BlueskyFactory.INSTANCE.instance(url);

    ServerCreateSessionRequest request = new ServerCreateSessionRequest();
    request.setIdentifier(identifier);
    request.setPassword(password);
    String accessJwt = bsky.server().createSession(request).getData().getAccessJwt();

    auth = new BearerTokenAuthProvider(accessJwt, null);
  }

  // TODO: String to Message
  public List<Message> getHomeTimeline() {

    FeedResource feedApi = bsky.feed();
    FeedGetTimelineRequest timelineRequest = new FeedGetTimelineRequest(auth);
    Response<FeedGetTimelineResponse> response = feedApi.getTimeline(timelineRequest);
    List<FeedDefsFeedViewPost> posts = response.getData().getFeed();

    List<Message> returnValue = new ArrayList<>();
    for (FeedDefsFeedViewPost viewPost : posts) {
      var post = viewPost.getPost();
      var record = post.getRecord();
      if (record instanceof FeedPost feedPost) {
        // Author の取得
        String poster = post.getAuthor().getDisplayName();
        String text = feedPost.getText();

        // CreatedAt の取得
        String createdAtString = feedPost.getCreatedAt();
        Instant postedAt = Instant.parse(createdAtString);

        returnValue.add(new Message(poster, text, postedAt));
      }
    }

    return returnValue;
  }
}
