package com.otakuzi.backend.entity.shop;

import com.otakuzi.backend.entity.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
    name = "shop_bookmark",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_shop_bookmark_user_shop",
            columnNames = {"user_id", "shop_id"}
        )
    }
)
public class ShopBookmark {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public ShopBookmark(User user, Shop shop) {
        this.user = user;
        this.shop = shop;
    }
}
