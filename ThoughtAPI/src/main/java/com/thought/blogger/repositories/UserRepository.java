package com.thought.blogger.repositories;

import com.thought.blogger.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT u FROM User u WHERE u.id = ?1")
    @NonNull
    Optional<User> findById(@NonNull Integer userId);

    @Query(value = "SELECT u FROM User u WHERE u.email = ?1")
    @NonNull
    Optional<User> findByEmail(@NonNull String email);

    @Query(value = "SELECT password FROM User WHERE password = ?1")
    @NonNull
    String findByPass(@NonNull String password);
}