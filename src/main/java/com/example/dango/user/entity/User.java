package com.example.dango.user.entity;


import com.example.dango.global.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;


@DynamicInsert
@DynamicUpdate
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`user`")
@Entity
public class User extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable=false)
    private Long kakaoId; //로그인할 때 사용하는 아이디(이메일)

    //@Column(nullable=false)
    private String password;

    @Column(nullable=false)
    private String name;

    //@Column(nullable=false)
    private String phone;

    @Column(name = "image_url")
    private String imageUrl;


    private String social;


    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private List<Authority> authorities;


    public static User toSocialLoginUser(Long kakao_id, String social, String name, String profileUrl) {
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .kakaoId(kakao_id)
                .name(name)
                .password("")  //소셜로그인은 비밀번호x
                .imageUrl(profileUrl)
                .authorities(Collections.singletonList(authority))
                .social(social)
                .build();

        return user;
    }

}
