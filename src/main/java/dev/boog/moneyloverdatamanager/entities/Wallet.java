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

    @Column(name = "wallet_name", nullable = false)
    private String walletName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Wallet userId(String userId) {
        if (this.user == null) {
            this.user = new User();
        }
        this.user.setId(Long.parseLong(userId));
        return this;
    }

}