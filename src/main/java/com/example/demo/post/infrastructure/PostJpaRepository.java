package com.example.demo.post.infrastructure;

import com.example.demo.post.domain.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<PostEntity, Long> {

}