package dev.boog.moneyloverdatamanager.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.*;


@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"user\"")
@Entity
public class User extends BaseEntity {

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

}