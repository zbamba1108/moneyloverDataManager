package dev.boog.moneyloverdatamanager.entities;

import dev.boog.moneyloverdatamanager.utils.Constants;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.proxy.HibernateProxy;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wallet", uniqueConstraints = {
        @UniqueConstraint( name = "walletNameAndUserId", columnNames = {"name", "user_id"})
})
@NamedEntityGraph(
        name = Constants.EntityGraph.WALLET_TRANSACTION,
        attributeNodes = {
                @NamedAttributeNode("transactionList"),
                @NamedAttributeNode(value = "transactionList", subgraph = Constants.EntityGraph.WALLET_TRANSACTION)
        },
        subgraphs = {
                @NamedSubgraph(
                        name = Constants.EntityGraph.WALLET_TRANSACTION,
                        attributeNodes = {
                                @NamedAttributeNode("category")
                        }
                )
        }
)
public class Wallet extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

    @OneToMany(mappedBy = "wallet", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Transaction> transactionList;

    public Wallet userId(String userId) {
        if (this.user == null) {
            this.user = new User();
        }
        this.user.setId(Long.parseLong(userId));
        return this;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Wallet wallet = (Wallet) o;
        return getId() != null && Objects.equals(getId(), wallet.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}