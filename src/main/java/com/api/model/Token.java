package com.api.model;

import com.api.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "token")
public class Token extends BaseEntity {
    public String token;
    private LocalDateTime expiresAt;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;
}
