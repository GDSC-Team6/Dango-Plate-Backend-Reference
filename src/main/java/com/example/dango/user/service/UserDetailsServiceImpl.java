package com.example.dango.user.service;

import com.example.dango.global.exception.BadRequestException;
import com.example.dango.user.dto.UserRes;
import com.example.dango.user.entity.User;
import com.example.dango.user.entity.UserDetailsImpl;
import com.example.dango.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(final String username) {
        UserRes.RoleDto roleDto = getUserRole(username);
        return new UserDetailsImpl(roleDto);
    }

    public UserRes.RoleDto getUserRole(String username) {
        User user;
        try {
            user = userRepository.findUserByUsername(username).get();
        }
        catch(NoSuchElementException e) {
            throw new BadRequestException("존재하지 않는 유저입니다.");
        }
        List<String> userRole = new ArrayList<>();
        userRole.add(user.getUserRole());
        return UserRes.RoleDto.builder()
                .id(user.getId())
                .password(user.getPassword())
                .roles(userRole)
                .build();
    }

}