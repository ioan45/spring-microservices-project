package com.example.postmicroservice.repositories;

import com.example.postmicroservice.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
