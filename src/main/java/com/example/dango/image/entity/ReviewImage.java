package com.example.dango.image.entity;

import com.example.dango.global.entity.BaseEntity;
import com.example.dango.review.entity.Review;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "review_image")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "url")
    private String url;
}
