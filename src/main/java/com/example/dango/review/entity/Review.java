package com.example.dango.review.entity;

import com.example.dango.global.entity.BaseEntity;
import com.example.dango.image.entity.ReviewImage;
import com.example.dango.shop.entity.Shop;
import com.example.dango.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "review")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "review_content")
    private String reviewContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @OneToMany(fetch = FetchType.LAZY)
    private List<ReviewImage> reviewImages;
}
