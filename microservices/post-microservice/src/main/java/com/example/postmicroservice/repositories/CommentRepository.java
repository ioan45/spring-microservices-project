package com.example.postmicroservice.repositories;

import com.example.postmicroservice.entities.Comment;
import com.example.postmicroservice.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByPostOrderByTimestampAsc(Post post);
    void deleteByPost(Post post);
}
