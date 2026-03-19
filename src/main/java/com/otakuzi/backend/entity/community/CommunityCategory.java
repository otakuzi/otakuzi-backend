package com.otakuzi.backend.entity.community;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "community_category")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommunityCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer id;

    @Column(name = "category_name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
}
