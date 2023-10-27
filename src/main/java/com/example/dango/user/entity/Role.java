package com.example.dango.user.entity;


import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
public enum Role {
    ROLE_USER,
    ROLE_ADMIN
}
