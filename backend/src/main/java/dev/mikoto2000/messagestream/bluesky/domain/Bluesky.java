package dev.mikoto2000.messagestream.bluesky.domain;

import java.util.ArrayList;
import java.util.List;

import work.socialhub.kbsky.BlueskyFactory;
import work.socialhub.kbsky.api.app.bsky.FeedResource;
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedGetTimelineRequest;
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedGetTimelineResponse;
import work.socialhub.kbsky.api.entity.com.atproto.server.ServerCreateSessionRequest;
import work.socialhub.kbsky.api.entity.share.Response;
import work.socialhub.kbsky.auth.BearerTokenAuthProvider;
import work.socialhub.kbsky.model.app.bsky.feed.FeedDefsFeedViewPost;
import work.socialhub.kbsky.model.app.bsky.feed.FeedPost;

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
  public List<String> getHomeTimeline() {

    FeedResource feedApi = bsky.feed();
    FeedGetTimelineRequest timelineRequest = new FeedGetTimelineRequest(auth);
    Response<FeedGetTimelineResponse> response = feedApi.getTimeline(timelineRequest);
    List<FeedDefsFeedViewPost> posts = response.getData().getFeed();

    List<String> returnValue = new ArrayList<>();
    for (FeedDefsFeedViewPost post : posts) {
      System.out.println(post);
      var record = post.getPost().getRecord();
      if (record instanceof FeedPost) {
        returnValue.add("📨 投稿: " + ((FeedPost)record).getText());
        System.out.println("📨 投稿: " + ((FeedPost)record).getText());
      }
    }

    return returnValue;
  }
}
