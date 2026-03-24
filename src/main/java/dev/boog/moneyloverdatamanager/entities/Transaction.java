package dev.boog.moneyloverdatamanager.entities;

import dev.boog.moneyloverdatamanager.utils.Constants;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.util.Objects;


@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaction")
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = Constants.EntityGraph.TRANSACTION_WALLET_CATEGORY,
                attributeNodes = {
                        @NamedAttributeNode("wallet"),
                        @NamedAttributeNode("category")
                }
        ),
        @NamedEntityGraph(
                name = Constants.EntityGraph.TRANSACTION_CATEGORY,
                attributeNodes = {
                        @NamedAttributeNode("category")
                }
        )
})
public class Transaction extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = Constants.Transaction.ColumnsName.WALLET,
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_transaction_wallet")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private Wallet wallet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = Constants.Transaction.ColumnsName.EVENT,
        foreignKey = @ForeignKey(name = "fk_transaction_event")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = Constants.Transaction.ColumnsName.CATEGORY,
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_transaction_category")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private Category category;

    @Column(name = "amount", nullable = false, precision = 8, scale = 2)
    private BigDecimal amount;

    @Column(name = "comment")
    private String comment;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Transaction that = (Transaction) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}