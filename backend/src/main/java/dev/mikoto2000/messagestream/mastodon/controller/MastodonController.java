package dev.mikoto2000.messagestream.mastodon.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.mikoto2000.messagestream.mastodon.domain.Message;
import dev.mikoto2000.messagestream.mastodon.entity.MastodonService;
import dev.mikoto2000.messagestream.mastodon.model.AddInstanceRequest;
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

    return mastodonService.getInstances();
  }

  @PostMapping("mastodon/instances")
  public void addInstance(
      @AuthenticationPrincipal Jwt jwt,
      @RequestBody AddInstanceRequest air) {

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
}

