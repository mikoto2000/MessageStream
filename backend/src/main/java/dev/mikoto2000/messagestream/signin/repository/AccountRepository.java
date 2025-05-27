package dev.mikoto2000.messagestream.signin.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.mikoto2000.messagestream.signin.entity.Account;

/**
 * AccountRepository
 */
public interface AccountRepository extends JpaRepository<Account, UUID> {

  Account findByIssuerAndSub(String iss, String sub);

}
