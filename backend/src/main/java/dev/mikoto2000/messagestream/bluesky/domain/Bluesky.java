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
import work.socialhub.kbsky.model.app.bsky.feed.FeedDefsPostView;
import work.socialhub.kbsky.model.app.bsky.feed.FeedPost;
import work.socialhub.kbsky.model.share.RecordUnion;
import dev.mikoto2000.messagestream.bluesky.domain.Message;
import work.socialhub.kbsky.model.app.bsky.embed.EmbedViewUnion;
import work.socialhub.kbsky.model.app.bsky.embed.EmbedImagesView;
import work.socialhub.kbsky.model.app.bsky.embed.EmbedImagesViewImage;

/**
 * Bluesky
 */
public class Bluesky {

  private final work.socialhub.kbsky.Bluesky bsky;
  private final String url;
  private final BearerTokenAuthProvider auth;

  public Bluesky(
      String url,
      String identifier,
      String password) {

    this.url = url;
    bsky = BlueskyFactory.INSTANCE.instance(url);

    ServerCreateSessionRequest request = new ServerCreateSessionRequest();
    request.setIdentifier(identifier);
    request.setPassword(password);
    String accessJwt = bsky.server().createSession(request).getData().getAccessJwt();

    auth = new BearerTokenAuthProvider(accessJwt, null);
  }

  public List<Message> getHomeTimeline() {

    FeedResource feedApi = bsky.feed();
    FeedGetTimelineRequest timelineRequest = new FeedGetTimelineRequest(auth);
    Response<FeedGetTimelineResponse> response = feedApi.getTimeline(timelineRequest);
    List<FeedDefsFeedViewPost> posts = response.getData().getFeed();

    List<Message> returnValue = new ArrayList<>();
    for (FeedDefsFeedViewPost viewPost : posts) {
      // リプライを除外
      if (viewPost.getReply() != null) {
        continue;
      }
      FeedDefsPostView post = viewPost.getPost();
      RecordUnion record = post.getRecord();
      if (record instanceof FeedPost feedPost) {
        String poster = post.getAuthor().getDisplayName();
        String iconUrl = post.getAuthor().getAvatar();
        String text = feedPost.getText();
        Instant postedAt = Instant.parse(feedPost.getCreatedAt());
        String link = convertToHttpUrl(post.getUri());
        List<String> thumbnailUrls = new ArrayList<>();
        List<String> imageUrls = new ArrayList<>();
        EmbedViewUnion embed = post.getEmbed();
        if (embed instanceof EmbedImagesView images) {
          for (EmbedImagesViewImage img : images.getImages()) {
            thumbnailUrls.add(img.getThumb());
            imageUrls.add(img.getFullsize());
          }
        }
        String serviceName = String.format("Bluesky - %s", this.url);
        returnValue.add(new Message(serviceName, poster, iconUrl, text, postedAt, link,
            thumbnailUrls, imageUrls));
      }
    }

    return returnValue;
  }

  private static String convertToHttpUrl(String atUri) {
    if (atUri == null || !atUri.startsWith("at://")) {
      return atUri;
    }
    String withoutScheme = atUri.substring("at://".length());
    int slash = withoutScheme.indexOf('/');
    if (slash < 0) {
      return atUri;
    }
    String did = withoutScheme.substring(0, slash);
    String rest = withoutScheme.substring(slash + 1);
    String[] parts = rest.split("/", 2);
    if (parts.length < 2) {
      return atUri;
    }
    String collection = parts[0];
    String rkey = parts[1];
    String pathSegment = collection.endsWith(".post") ? "post" : collection;
    return String.format("https://bsky.app/profile/%s/%s/%s", did, pathSegment, rkey);
  }
}
