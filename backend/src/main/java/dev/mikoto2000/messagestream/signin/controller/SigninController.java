package dev.mikoto2000.messagestream.signin.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;

import dev.mikoto2000.messagestream.signin.service.SigninService;
import lombok.RequiredArgsConstructor;

/**
 * SigninController
 */
@RequiredArgsConstructor
public class SigninController {

  private final SigninService signinService;

  @PostMapping("/register")
  public void register(
      @AuthenticationPrincipal Jwt jwt) {

    signinService.register(
        jwt.getClaimAsString("iss"),
        jwt.getClaimAsString("sub"));
  }
}

