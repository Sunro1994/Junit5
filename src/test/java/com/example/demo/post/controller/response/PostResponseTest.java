package com.example.demo.post.controller.response;

import com.example.demo.post.domain.Post;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PostResponseTest {

    @Test
    public void Post로_응답을_생성할_수_있다() {
        //given
        Post post = Post.builder().
                content("test")
                .writer(User.builder()
                        .email("tjsfhdhkd@naver.com")
                        .nickname("tester")
                        .address("Seoul")
                        .status(UserStatus.ACTIVE)
                        .certificationCode("a-a-a")
                        .build())
                .build();

        //when
        PostResponse postResponse = PostResponse.from(post);

        //then
        Assertions.assertThat(postResponse.getContent()).isEqualTo(post.getContent());
        Assertions.assertThat(postResponse.getId()).isEqualTo(post.getId());
        Assertions.assertThat(postResponse.getCreatedAt()).isEqualTo(post.getCreatedAt());
        Assertions.assertThat(postResponse.getWriter().getStatus()).isEqualTo(post.getWriter().getStatus());

    }


}