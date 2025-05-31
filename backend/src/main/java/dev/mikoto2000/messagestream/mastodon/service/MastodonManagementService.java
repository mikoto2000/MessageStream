package dev.mikoto2000.messagestream.mastodon.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import dev.mikoto2000.messagestream.bluesky.domain.Bluesky;
import dev.mikoto2000.messagestream.bluesky.entity.BlueskyService;
import dev.mikoto2000.messagestream.bluesky.repository.BlueskyServiceRepository;
import dev.mikoto2000.messagestream.mastodon.domain.Mastodon;
import dev.mikoto2000.messagestream.mastodon.domain.Message;
import dev.mikoto2000.messagestream.mastodon.entity.MastodonService;
import dev.mikoto2000.messagestream.mastodon.repository.MastodonServiceRepository;
import dev.mikoto2000.messagestream.signin.entity.Account;
import dev.mikoto2000.messagestream.signin.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * MastodonManagementService
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MastodonManagementService {

  private final AccountRepository accountRepository;
  private final MastodonServiceRepository mastodonServiceRepository;

  public List<MastodonService> getInstances() {
    return mastodonServiceRepository.findAll();
  }

  public void addInstance(
      String iss,
      String sub,
      String instanceUrl,
      String accessToken) {

    log.info("Start addInstance by: ({}, {}, {})", iss, sub, instanceUrl);

    var account = accountRepository.findByIssuerAndSub(iss, sub);

    log.info("target account: {}", account);

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

    // TODO: インスタンスを毎回 new しないようにする
    List<Message> messages = new ArrayList<>();
    for (var mastodon : mastodons) {
      var m = new Mastodon(
          mastodon.getUrl(),
          mastodon.getAccessToken());

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

    // TODO: インスタンスを毎回 new しないようにする
    List<Message> messages = new ArrayList<>();
    for (var mastodon : mastodons) {
      var m = new Mastodon(
          mastodon.getUrl(),
          mastodon.getAccessToken());

      messages.addAll(m.getPublicTimeline());
    }

    log.info("End getHomeTimeline");

    return messages;
  }
}
