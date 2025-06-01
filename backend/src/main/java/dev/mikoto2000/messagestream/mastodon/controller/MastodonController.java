package dev.mikoto2000.messagestream.mastodon.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

import dev.mikoto2000.messagestream.mastodon.domain.Message;
import dev.mikoto2000.messagestream.mastodon.entity.MastodonService;
import dev.mikoto2000.messagestream.mastodon.model.AddMastodonInstanceRequest;
import dev.mikoto2000.messagestream.mastodon.service.MastodonManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * MastodonController
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class MastodonController {

  private final MastodonManagementService mastodonService;

  @GetMapping("mastodon/instances")
  public List<MastodonService> getInstances(
      @AuthenticationPrincipal Jwt jwt) {

    return mastodonService.getInstances(
        jwt.getClaimAsString("iss"),
        jwt.getClaimAsString("sub"));
  }

  @PostMapping("mastodon/instances")
  public void addInstance(
      @AuthenticationPrincipal Jwt jwt,
      @RequestBody AddMastodonInstanceRequest air) {

    log.info("air: {}", air);

    mastodonService.addInstance(
        jwt.getClaimAsString("iss"),
        jwt.getClaimAsString("sub"),
        air.getInstanceUrl(),
        air.getAccessToken());
  }

  @PostMapping("mastodon/home")
  public List<Message> getHomeTimeline(
      @AuthenticationPrincipal Jwt jwt) {

    return mastodonService.getHomeTimeline(
        jwt.getClaimAsString("iss"),
        jwt.getClaimAsString("sub"));
  }

  @PostMapping("mastodon/pub")
  public List<Message> getPublicTimeline(
      @AuthenticationPrincipal Jwt jwt) {

    return mastodonService.getPublicTimeline(
        jwt.getClaimAsString("iss"),
        jwt.getClaimAsString("sub"));
  }

  @DeleteMapping("mastodon/instances/{id}")
  public void deleteInstance(
      @AuthenticationPrincipal Jwt jwt,
      @PathVariable("id") UUID id) {

    mastodonService.deleteInstance(
        jwt.getClaimAsString("iss"),
        jwt.getClaimAsString("sub"),
        id);
  }
}
