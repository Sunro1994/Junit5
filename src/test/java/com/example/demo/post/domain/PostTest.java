package com.example.demo.post.domain;

import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PostTest {

    @Test
            public void PostCreate로_게시물을_만들_수_있다(){
        //given
        PostCreate postCreate = PostCreate
                .builder()
                .writerId(1)
                .content("hellowWorld")
                .build();

        User writer = User.builder()
                .id(1L)
                .email("tjsfhdhkd@naver.com")
                .nickname("tester")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("a-a-a")
                .build();

        //when
        Post post = Post.from(writer, postCreate);

        //then
        Assertions.assertThat(post.getContent()).isEqualTo("hellowWorld");
        Assertions.assertThat(post.getWriter().getEmail()).isEqualTo("tjsfhdhkd@naver.com");
        Assertions.assertThat(post.getWriter().getNickname()).isEqualTo("tester");
        Assertions.assertThat(post.getWriter().getAddress()).isEqualTo("Seoul");
        Assertions.assertThat(post.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
        Assertions.assertThat(post.getWriter().getCertificationCode()).isEqualTo("a-a-a");
    }

}