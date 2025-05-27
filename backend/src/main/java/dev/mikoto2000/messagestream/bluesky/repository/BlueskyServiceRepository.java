package dev.mikoto2000.messagestream.bluesky.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.mikoto2000.messagestream.bluesky.entity.BlueskyService;

/**
 * BlueskyServiceRepository
 */
public interface BlueskyServiceRepository extends JpaRepository<BlueskyService, UUID> {

  List<BlueskyService> findByAccountId(UUID id);
}
