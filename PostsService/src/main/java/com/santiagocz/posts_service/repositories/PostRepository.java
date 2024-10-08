package com.santiagocz.posts_service.repositories;

import com.santiagocz.posts_service.entities.Post;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository <Post, Long> {

    @Query("SELECT p FROM Post p WHERE p.userId = :userId")
    List<Post> findPostByUserId(Long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Post p WHERE p.userId = :userId")
    void deleteByUserId(@Param("userId") Long userId);
}
