package dev.boog.moneyloverdatamanager.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@SuperBuilder
@Entity
@Table(name = "budget")
@NoArgsConstructor
@AllArgsConstructor
public class Budget extends BaseEntity {

    @Column(name = "category_id", nullable = false)
    private Long category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "user_id",
        nullable = false,
        foreignKey = @ForeignKey( name = "fk_budget_user")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private Long endDate;

    @Column(name = "amount", nullable = false, precision = 8, scale = 2)
    private BigDecimal amount;

    public Budget userId(Long userId) {
        if (this.user == null) {
            this.user = new User();
        }
        this.user.setId(userId);
        return this;
    }

}