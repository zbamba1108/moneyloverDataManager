package dev.boog.moneyloverdatamanager.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.*;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wallet", uniqueConstraints = {
        @UniqueConstraint( name = "walletNameAndUserId", columnNames = { "wallet_name", "user_id"})
})
public class Wallet extends BaseEntity {

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