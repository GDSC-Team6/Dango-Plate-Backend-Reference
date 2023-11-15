package com.example.dango.user.repository;

import com.example.dango.user.entity.Authority;
import com.example.dango.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
    Optional<Authority> findByAuthorityName(String name);
}
