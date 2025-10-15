package org.example.mvcpractice.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "person",
        uniqueConstraints = @UniqueConstraint(name = "uq_person_email", columnNames = "email"),
        indexes = {
        @Index(name = "ix_person_name_last_name", columnList = "name,last_name")
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 80)
    private String name;

    @Column(name = "last_name", nullable = false, length = 120)
    private String lastName;

    @Column( nullable = false, length = 220,unique = true)
    private String email;

    @Column(name = "number", nullable = false, length = 30)
    private String number;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at",nullable = false)
    private LocalDateTime updatedAt;
}
