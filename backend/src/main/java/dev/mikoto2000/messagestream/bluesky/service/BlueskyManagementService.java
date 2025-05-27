package dev.mikoto2000.messagestream.bluesky.service;

import java.util.List;

import org.springframework.stereotype.Service;

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

  public List<dev.mikoto2000.messagestream.bluesky.entity.BlueskyService> getInstances() {
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

    blueskyServiceRepository.save(new dev.mikoto2000.messagestream.bluesky.entity.BlueskyService(
        null,
        account.getId(),
        instanceUrl,
        handle,
        password));
  }
}
