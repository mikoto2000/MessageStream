package dev.mikoto2000.messagestream.signin.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.mikoto2000.messagestream.signin.service.SigninService;
import lombok.RequiredArgsConstructor;

/**
 * SigninController
 */
@RestController
@RequiredArgsConstructor
public class SigninController {

  private final SigninService signinService;

  @PostMapping("signin/register")
  public void register(
      @AuthenticationPrincipal Jwt jwt) {

    signinService.register(
        jwt.getClaimAsString("iss"),
        jwt.getClaimAsString("sub"));
  }

  @PostMapping("signin/exists")
  public boolean exists(
      @AuthenticationPrincipal Jwt jwt) {

    return signinService.exists(
        jwt.getClaimAsString("iss"),
        jwt.getClaimAsString("sub"));
  }
}

