package com.example.demo.medium;

import com.example.demo.common.domain.exception.ResourceNotFoundException;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.domain.request.UserCreate;
import com.example.demo.user.infrastructure.UserJpaRepository;
import com.example.demo.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@TestPropertySource("classpath:application.properties")
@SqlGroup({
        @Sql(value = "/sql/user-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class UserServiceTest {

    @Autowired
    private UserService userService;
    @MockBean //실제 객체대신 Bean을 주입해준다.
    private JavaMailSenderImpl mailSender;
    @Autowired
    private UserJpaRepository userJpaRepository;

    @Test
    void getByEmail은_ACTIVE_상태인_유저를_찾아올_수_있다() {
        //given
        String email = "tjsfhdhkd@naver.com";

        //when
        User result = userService.getByEmail(email);

        //then
        Assertions.assertThat(result.getEmail()).isEqualTo(email);
    }

    @Test
    void getByEmail은_PENDING_상태인_유저는_찾아올_수_없다() {
        //given
        String email = "tjsfhdhkd2@naver.com";

        //when

        //then
        assertThatThrownBy(() -> {
            User result = userService.getByEmail(email);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void userCreateDto_를_이용하여_유저를_생성할_수_있다() {
        //when
        UserCreate userCreateDto = UserCreate.builder()
                .email("tjsfhdhkd3@naver.com")
                .address("Kyeong")
                .nickname("sunroof123")
                .build();
        BDDMockito.doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        //when
        User result = userService.create(userCreateDto);

        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getStatus()).isEqualTo(UserStatus.PENDING);
    }

    @Test
    @Transactional
    void user를_로그인_시키면_마지막_로그인_시간이_변경된다(){
        //given

        //when
        userService.login(1);
        User user = userService.getById(1);
        System.out.println(user.getLastLoginAt());

        //then
        assertThat(user.getLastLoginAt()).isGreaterThan(0);
    }
}