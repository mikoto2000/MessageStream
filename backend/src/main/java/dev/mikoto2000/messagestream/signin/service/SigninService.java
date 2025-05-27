package dev.mikoto2000.messagestream.signin.service;

import org.springframework.stereotype.Service;

import dev.mikoto2000.messagestream.signin.entity.Account;
import dev.mikoto2000.messagestream.signin.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * SigninService
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SigninService {

  private final AccountRepository accountRepository;

  public void register(String iss, String sub) {
    Account newAccount = accountRepository.save(new Account(
        null,
        iss,
        sub));

    log.info("Create new Account({}", newAccount);
  }
}

