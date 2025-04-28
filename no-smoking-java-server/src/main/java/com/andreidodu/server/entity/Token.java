package com.andreidodu.server.entity;

import com.andreidodu.server.entity.common.ModelCommon;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "ns_token")
@EntityListeners(AuditingEntityListener.class)
public class Token extends ModelCommon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String token;

    @Column(name = "expiration_datetime", nullable = false)
    private LocalDateTime expirationDatetime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}

