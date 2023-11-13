package com.example.dango.shop.entity;

import com.example.dango.global.entity.BaseEntity;
import com.example.dango.image.entity.ShopImage;
import com.example.dango.review.entity.Review;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "shop")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Shop extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shop_uid", nullable = false, unique = true)
    private Long shopUid;

    @OneToMany(mappedBy = "shop", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ShopImage> shopImages;

    @OneToMany(mappedBy = "shop", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Review> reviews;
}
