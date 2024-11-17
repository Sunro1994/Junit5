package com.example.demo.user.domain.response;

import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class UserResponseTest {

    @Test
    public void User으로_응답을_생성할_수_있다(){
        //given
        User writer = User.builder()
                .id(1L)
                .email("tjsfhdhkd@naver.com")
                .nickname("tester")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("a-a-a")
                .lastLoginAt(100L)
                .build();
        //when
        UserResponse userResponse = UserResponse.from(writer);

        //then
        assertThat(userResponse.getId()).isEqualTo(1L);
        assertThat(userResponse.getEmail()).isEqualTo("tjsfhdhkd@naver.com");
        assertThat(userResponse.getNickname()).isEqualTo("tester");
        assertThat(userResponse.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(userResponse.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(userResponse.getLastLoginAt()).isEqualTo(100L);
    }

}