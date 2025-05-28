package dev.mikoto2000.messagestream.mastodon.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.mikoto2000.messagestream.mastodon.entity.MastodonService;

/**
 * MastodonServiceRepository
 */
public interface MastodonServiceRepository extends JpaRepository<MastodonService, UUID> {

  List<MastodonService> findByAccountId(UUID id);
}

