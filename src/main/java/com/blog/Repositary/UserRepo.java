package com.blog.Repositary;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.Entity.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer>{

    public Optional<User> findByEmail(String email);
}
