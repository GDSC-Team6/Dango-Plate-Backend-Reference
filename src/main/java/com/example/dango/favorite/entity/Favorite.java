package com.example.dango.favorite.entity;

import com.example.dango.shop.entity.Shop;
import com.example.dango.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "favorite")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;
}
