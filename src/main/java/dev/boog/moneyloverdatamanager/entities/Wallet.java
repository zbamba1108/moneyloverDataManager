package dev.boog.moneyloverdatamanager.entities;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wallet", uniqueConstraints = {
        @UniqueConstraint( name = "walletNameAndUserId", columnNames = { "wallet_name", "user_id"})
})
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "wallet_name", nullable = false, unique = true)
    private String walletName;

    @Column(name = "user_id", nullable = false)
    private Long userId;

}