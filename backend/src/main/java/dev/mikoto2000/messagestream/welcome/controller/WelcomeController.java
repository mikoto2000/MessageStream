package dev.mikoto2000.messagestream.welcome.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.mikoto2000.messagestream.bluesky.service.BlueskyService;
import lombok.RequiredArgsConstructor;

/**
 * WelcomeController
 */
@RestController
@RequiredArgsConstructor
public class WelcomeController {

  @GetMapping({ "/", "/index" })
  public static String welcome(
      @AuthenticationPrincipal Jwt jwt) {
    return String.format("Hello, %s of %s!", jwt.getClaimAsString("sub"), jwt.getClaimAsString("issr"));
  }

}
