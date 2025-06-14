package dev.mikoto2000.messagestream.mastodon.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import dev.mikoto2000.messagestream.mastodon.domain.Mastodon;
import dev.mikoto2000.messagestream.mastodon.domain.Message;
import dev.mikoto2000.messagestream.mastodon.entity.MastodonService;
import dev.mikoto2000.messagestream.mastodon.repository.MastodonServiceRepository;
import dev.mikoto2000.messagestream.signin.entity.Account;
import dev.mikoto2000.messagestream.signin.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * MastodonManagementService
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MastodonManagementService {

  private final AccountRepository accountRepository;
  private final MastodonServiceRepository mastodonServiceRepository;

  private final Cache<UUID, Mastodon> mastodonCache =
      CacheBuilder.newBuilder()
          .expireAfterAccess(10, TimeUnit.MINUTES)
          .build();

  public List<MastodonService> getInstances(
      String iss,
      String sub) {
    log.info("Start getInstances by: ({}, {})", iss, sub);
    var account = accountRepository.findByIssuerAndSub(iss, sub);
    log.info("target account: {}", account);
    return mastodonServiceRepository.findByAccountId(account.getId());
  }

  public void addInstance(
      String iss,
      String sub,
      String instanceUrl,
      String accessToken) {

    log.info("Start addInstance by: ({}, {}, {})", iss, sub, instanceUrl);

    var account = accountRepository.findByIssuerAndSub(iss, sub);

    log.info("target account: {}", account);

    // Validate connection to the Mastodon instance with provided credentials
    try {
      log.debug("Testing connection to Mastodon instance: url={}", instanceUrl);
      new Mastodon(instanceUrl, accessToken).getHomeTimeline();
    } catch (Exception e) {
      log.error("Failed to connect to Mastodon instance url={}", instanceUrl, e);
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST,
          "Failed to connect to Mastodon instance: " + e.getMessage(),
          e);
    }

    mastodonServiceRepository.save(new MastodonService(
        null,
        account.getId(),
        instanceUrl,
        accessToken));
  }

  public List<Message> getHomeTimeline(
      String iss,
      String sub) {
    log.info("Start getHomeTimeline");

    Account account = accountRepository.findByIssuerAndSub(iss, sub);

    log.info("account: {}", account);

    List<MastodonService> mastodons = mastodonServiceRepository.findByAccountId(account.getId());

    log.info("mastodon: {}", mastodons);

    List<Message> messages = new ArrayList<>();
    for (var mastodon : mastodons) {
      Mastodon m;
      try {
        m = mastodonCache.get(
            mastodon.getId(),
            () -> {
              log.info("Creating new Mastodon client for service: {}", mastodon.getId());
              return new Mastodon(
                mastodon.getUrl(),
                mastodon.getAccessToken());
            });
      } catch (ExecutionException e) {
        throw new RuntimeException("Failed to load Mastodon client", e);
      }
      messages.addAll(m.getHomeTimeline());
    }

    log.info("End getHomeTimeline");

    return messages;
  }

  public List<Message> getPublicTimeline(
      String iss,
      String sub) {
    log.info("Start getPublicTimeline");

    Account account = accountRepository.findByIssuerAndSub(iss, sub);

    log.info("account: {}", account);

    List<MastodonService> mastodons = mastodonServiceRepository.findByAccountId(account.getId());

    log.info("mastodon: {}", mastodons);

    Set<Message> messages = new HashSet<>();
    for (var mastodon : mastodons) {
      Mastodon m;
      try {
        m = mastodonCache.get(
            mastodon.getId(),
            () -> {
              log.info("Creating new Mastodon client for service: {}", mastodon.getId());
              return new Mastodon(
                mastodon.getUrl(),
                mastodon.getAccessToken());
            });
      } catch (ExecutionException e) {
        throw new RuntimeException("Failed to load Mastodon client", e);
      }
      messages.addAll(m.getPublicTimeline());
    }

    log.info("End getHomeTimeline");

    return new ArrayList<>(messages);
  }

  public void deleteInstance(
      String iss,
      String sub,
      UUID instanceId) {
    log.info("Deleting Mastodon instance: id={}", instanceId);
    var account = accountRepository.findByIssuerAndSub(iss, sub);
    var service = mastodonServiceRepository.findById(instanceId)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Mastodon instance not found: " + instanceId));
    if (!service.getAccountId().equals(account.getId())) {
      throw new ResponseStatusException(
          HttpStatus.FORBIDDEN,
          "Cannot delete Mastodon instance that does not belong to user: " + instanceId);
    }
    mastodonServiceRepository.delete(service);
    mastodonCache.invalidate(instanceId);
  }
}
