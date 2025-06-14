package dev.mikoto2000.messagestream.bluesky.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.mikoto2000.messagestream.bluesky.domain.Message;
import dev.mikoto2000.messagestream.bluesky.entity.BlueskyService;
import dev.mikoto2000.messagestream.bluesky.model.AddBlueskyInstanceRequest;
import dev.mikoto2000.messagestream.bluesky.service.BlueskyManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * BlueskyController
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class BlueskyController {

  private final BlueskyManagementService blueskyService;

  @GetMapping("bluesky/instances")
  public List<BlueskyService> getInstances(
      @AuthenticationPrincipal Jwt jwt) {

    return blueskyService.getInstances(
        jwt.getClaimAsString("iss"),
        jwt.getClaimAsString("sub"));
  }

  @PostMapping("bluesky/instances")
  public void addInstance(
      @AuthenticationPrincipal Jwt jwt,
      @RequestBody AddBlueskyInstanceRequest air) {

    log.info("air: {}", air);

    blueskyService.addInstance(
        jwt.getClaimAsString("iss"),
        jwt.getClaimAsString("sub"),
        air.getInstanceUrl(),
        air.getHandle(),
        air.getPassword());
  }

  @PostMapping("bluesky/home")
  public List<Message> getHomeTimeline(
      @AuthenticationPrincipal Jwt jwt) {

    return blueskyService.getHomeTimeline(
        jwt.getClaimAsString("iss"),
        jwt.getClaimAsString("sub"));
  }

  @DeleteMapping("bluesky/instances/{id}")
  public void deleteInstance(
      @AuthenticationPrincipal Jwt jwt,
      @PathVariable("id") UUID id) {

    blueskyService.deleteInstance(
        jwt.getClaimAsString("iss"),
        jwt.getClaimAsString("sub"),
        id);
  }
}
