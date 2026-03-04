package dev.boog.moneyloverdatamanager.entities;

import dev.boog.moneyloverdatamanager.utils.Constants;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
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
    private User user;

    @OneToMany(mappedBy = "wallet", fetch = FetchType.LAZY)
    private List<Transaction> transactionList;

    public Wallet userId(String userId) {
        if (this.user == null) {
            this.user = new User();
        }
        this.user.setId(Long.parseLong(userId));
        return this;
    }

}