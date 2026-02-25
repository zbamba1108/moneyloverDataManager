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
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = Constants.Transaction.ColumnsName.WALLET, nullable = false)
    private Wallet wallet;

    @Column(name = Constants.Transaction.ColumnsName.USER, nullable = false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = Constants.Transaction.ColumnsName.EVENT)
    private Event event;

    @ManyToOne
    @JoinColumn(name = Constants.Transaction.ColumnsName.CATEGORY, nullable = false)
    private Category category;

    @Column(name = "amount", nullable = false, precision = 8, scale = 2)
    private BigDecimal amount;

    @Column(name = "comment", nullable = false)
    private String comment;

}