package com.example.authDemo.repository;

import com.example.authDemo.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {

    Optional<UserEntity> findByEmail(String email);
    Boolean existsByEmail(String email);

    Boolean existsByEmailAndId(String email,int id);

    Optional<UserEntity> findByEmailAndId(String email,int id);
    
}
