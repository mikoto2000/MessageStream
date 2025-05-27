package dev.mikoto2000.messagestream.signin.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Account
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
  @Id
  @GeneratedValue
  private UUID id;
  private String issuer;
  private String sub;
}

