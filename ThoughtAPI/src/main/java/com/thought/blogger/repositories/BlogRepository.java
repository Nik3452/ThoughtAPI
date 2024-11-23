package com.thought.blogger.repositories;

import com.thought.blogger.models.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog, Integer> {

    @Query(value = "SELECT b FROM Blog b WHERE b.id = ?1")
    @NonNull
    Optional<Blog> findById(@NonNull Integer blogId);

    @Query(value = "SELECT b FROM Blog b WHERE b.owner = ?1")
    @NonNull
    Optional<Blog> findByOwner(@NonNull String owner);

    @Query(value = "SELECT b FROM Blog b WHERE b.category = ?1")
    @NonNull
    Optional<Blog> findByCategory(@NonNull String category);

    @Query(value = "SELECT b FROM Blog b WHERE b.title = ?1")
    @NonNull
    Collection<? extends Blog> findByTitle(@NonNull String title);
}