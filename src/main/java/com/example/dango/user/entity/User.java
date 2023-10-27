package com.example.dango.user.entity;


import com.example.dango.global.entity.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;


@DynamicInsert
@DynamicUpdate
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
@Entity
public class User extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String username; //로그인할 때 사용하는 아이디(이메일)

    private String password;

    private String name;

    private String phone;

    private String birth;

    @Column(name = "image_url")
    private String imageUrl;

    private String social;

    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private Role role;



    //TODO dto로 수정하기 나중에
    public static User toSocialLoginUser(String email, String social, String name) {

        User user = User.builder()
                .username(email)
                .name(name)
                .password("")  //소셜로그인은 비밀번호x
                .imageUrl("이미지url")
                .role(Role.ROLE_USER)
                .social(social)
                .build();

        return user;
    }

}
