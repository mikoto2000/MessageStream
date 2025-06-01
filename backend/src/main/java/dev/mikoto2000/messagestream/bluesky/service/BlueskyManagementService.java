package dev.mikoto2000.messagestream.bluesky.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import org.springframework.stereotype.Service;

import dev.mikoto2000.messagestream.bluesky.domain.Bluesky;
import dev.mikoto2000.messagestream.bluesky.domain.Message;
import dev.mikoto2000.messagestream.bluesky.entity.BlueskyService;
import dev.mikoto2000.messagestream.bluesky.repository.BlueskyServiceRepository;
import dev.mikoto2000.messagestream.signin.entity.Account;
import dev.mikoto2000.messagestream.signin.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * BlueskyService
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BlueskyManagementService {

  private final AccountRepository accountRepository;
  private final BlueskyServiceRepository blueskyServiceRepository;
  private final Cache<UUID, Bluesky> bskyCache =
      CacheBuilder.newBuilder()
          .expireAfterAccess(10, TimeUnit.MINUTES)
          .build();

  public List<BlueskyService> getInstances() {
    return blueskyServiceRepository.findAll();
  }

  public void addInstance(
      String iss,
      String sub,
      String instanceUrl,
      String handle,
      String password) {

    log.info("Start addInstance by: ({}, {})", iss, sub);

    var account = accountRepository.findByIssuerAndSub(iss, sub);

    log.info("target account: {}", account);

    blueskyServiceRepository.save(new BlueskyService(
        null,
        account.getId(),
        instanceUrl,
        handle,
        password));
  }

  public List<Message> getHomeTimeline(
      String iss,
      String sub) {
    log.info("Start getHomeTimeline");

    Account account = accountRepository.findByIssuerAndSub(iss, sub);

    log.info("account: {}", account);

    List<BlueskyService> bskys = blueskyServiceRepository.findByAccountId(account.getId());

    log.info("bskys: {}", bskys);

    List<Message> messages = new ArrayList<>();
    for (var service : bskys) {
      Bluesky client;
      try {
        client = bskyCache.get(
            service.getId(),
            () -> {
              log.info("Creating new Bluesky client for service: {}", service.getId());
              return new Bluesky(
                service.getUrl(),
                service.getHandle(),
                service.getAppPassword());
            });
      } catch (ExecutionException e) {
        throw new RuntimeException("Failed to load Bluesky client", e);
      }
      messages.addAll(client.getHomeTimeline());
    }

    log.info("End getHomeTimeline");

    return messages;
  }
}
