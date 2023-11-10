package com.example.dango.shop.entity;

import com.example.dango.global.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

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
}
