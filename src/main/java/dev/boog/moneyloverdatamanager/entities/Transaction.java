package dev.boog.moneyloverdatamanager.entities;

import dev.boog.moneyloverdatamanager.utils.Constants;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "transaction")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(
        name = Constants.EntityGraph.TRANSACTION_WALLET_CATEGORY,
        attributeNodes = {
                @NamedAttributeNode("wallet"),
                @NamedAttributeNode("category")
        }
)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Constants.Transaction.ColumnsName.WALLET, nullable = false)
    private Wallet wallet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Constants.Transaction.ColumnsName.USER, nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = Constants.Transaction.ColumnsName.EVENT)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Constants.Transaction.ColumnsName.CATEGORY, nullable = false)
    private Category category;

    @Column(name = "amount", nullable = false, precision = 8, scale = 2)
    private BigDecimal amount;

    @Column(name = "comment")
    private String comment;

    public Transaction userId(String userId) {
        if (this.user == null) {
            this.user = new User();
        }
        this.user.setId(Long.parseLong(userId));
        return this;
    }

}