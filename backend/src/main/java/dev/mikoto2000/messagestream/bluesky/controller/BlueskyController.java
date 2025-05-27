package dev.mikoto2000.messagestream.bluesky.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.mikoto2000.messagestream.bluesky.entity.BlueskyService;
import dev.mikoto2000.messagestream.bluesky.model.AddInstanceRequest;
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

    return blueskyService.getInstances();
  }

  @PostMapping("bluesky/instances")
  public void addInstance(
      @AuthenticationPrincipal Jwt jwt,
      @RequestBody AddInstanceRequest air) {

    log.info("air: {}", air);

    blueskyService.addInstance(
        jwt.getClaimAsString("iss"),
        jwt.getClaimAsString("sub"),
        air.getInstanceUrl(),
        air.getHandle(),
        air.getPassword());
  }
}
