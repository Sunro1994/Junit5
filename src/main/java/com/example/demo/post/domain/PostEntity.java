package com.example.demo.post.domain;

import com.example.demo.user.domain.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "posts")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "modified_at")
    private Long modifiedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity writer;

    public static PostEntity fromModel(Post post) {
        PostEntity entity = new PostEntity();
        entity.setId(post.getId());
        entity.setContent(post.getContent());
        entity.setCreatedAt(post.getCreatedAt());
        entity.setModifiedAt(post.getModifiedAt());
        entity.setWriter(UserEntity.fromModel(post.getWriter()));
        return entity;
    }

    public Post toModel(){
        return Post.builder()
                .id(this.id)
                .content(this.content)
                .createdAt(this.createdAt)
                .modifiedAt(this.modifiedAt)
                .writer(this.writer.toModel())
                .build();
    }
}