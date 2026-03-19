package com.otakuzi.backend.entity.community;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "community_comment_map",
    uniqueConstraints = {
            @UniqueConstraint(
                    name = "uk_post_user",
                    columnNames = {"post_id", "user_id"}
            )
    }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityCommentMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_map_id")
    private Long id;

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "anonymous_number", nullable = false)
    private Integer anonymousNumber;
}
