package com.cydeo.entity;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass

public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean isDeleted = false;
    @Column(nullable=false,updatable = false)//ignore fields,just keep the data for creation time,do not change when update
    private LocalDateTime insertDateTime;
    @Column(nullable=false,updatable = false)
    private Long insertUserId;
    @Column(nullable=false)
    private LocalDateTime lastUpdateDateTime;
    @Column(nullable=false)
    private Long lastUpdateUserId;

    @PrePersist
    private void prePersist() {
        this.insertDateTime = LocalDateTime.now();
        this.insertUserId = 1L;
        this.lastUpdateDateTime = LocalDateTime.now();
        this.lastUpdateUserId = 1L;
    }
    @PreUpdate
    private void preUpdate() {
        this.lastUpdateDateTime = LocalDateTime.now();
        this.lastUpdateUserId = 1L;
    }

}
