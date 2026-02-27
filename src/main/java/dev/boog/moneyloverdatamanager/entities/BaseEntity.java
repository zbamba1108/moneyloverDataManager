package dev.boog.moneyloverdatamanager.entities;

import jakarta.persistence.*;
import java.sql.*;
import lombok.*;
import lombok.experimental.*;
import org.hibernate.annotations.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "created_at")
    private Timestamp createdAt;
}
