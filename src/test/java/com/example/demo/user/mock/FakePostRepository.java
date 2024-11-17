package com.example.demo.user.mock;

import com.example.demo.post.domain.Post;
import com.example.demo.post.service.port.PostRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FakePostRepository implements PostRepository {

    private Long autoGenratedId;
    private final List<Post> posts = new ArrayList<>();

    @Override
    public Optional<Post> findById(long id) {
        return posts.stream().filter(post -> post.getId() == id).findAny();
    }

    @Override
    public Post save(Post post) {
        if (post.getId() == 0 || post.getId() == null) {
            Post newPost = Post.builder()
                    .id(autoGenratedId++)
                    .writer(post.getWriter())
                    .createdAt(post.getCreatedAt())
                    .content(post.getContent())
                    .modifiedAt(post.getModifiedAt())
                    .build();
            posts.add(newPost);
            return newPost;
        }else{
            posts.removeIf(item->
                Objects.equals(item.getId(), post.getId()));
            posts.add(post);
            return post;
        }
    }
}
