package com.example.dango.user.repository;

import com.example.dango.user.entity.User;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUsername(String email);


    Optional<User> findUserByUserId(Long userId);


    User findByUsernameAndSocial(String username, String social);

    boolean existsByUsernameAndSocial(String username, String social);

    boolean existsByUsername(String username);
}
