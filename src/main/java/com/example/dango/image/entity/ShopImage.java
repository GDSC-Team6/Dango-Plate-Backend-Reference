package com.example.dango.image.entity;

import com.example.dango.global.entity.BaseEntity;
import com.example.dango.review.entity.Review;
import com.example.dango.shop.entity.Shop;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "shop_image")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "url")
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;
}
