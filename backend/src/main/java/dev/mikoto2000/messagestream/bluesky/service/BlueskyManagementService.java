package dev.mikoto2000.messagestream.bluesky.service;

import java.util.ArrayList;
import java.util.List;

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

    // TODO: インスタンスを毎回 new しないようにする
    List<Message> messages = new ArrayList<>();
    for (var bsky : bskys) {
      var b = new Bluesky(
          bsky.getUrl(),
          bsky.getHandle(),
          bsky.getAppPassword());

      messages.addAll(b.getHomeTimeline());
    }

    log.info("End getHomeTimeline");

    return messages;
  }
}
