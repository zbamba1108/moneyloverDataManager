package dev.boog.moneyloverdatamanager.entities;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@Entity
@Table(name = "wallet")
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "wallet_name", nullable = false)
    private String walletName;

    @Column(name = "user_id", nullable = false)
    private Long userId;

}