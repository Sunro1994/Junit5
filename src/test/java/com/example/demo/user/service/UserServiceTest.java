package com.example.demo.user.service;

import com.example.demo.common.domain.exception.ResourceNotFoundException;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.domain.request.UserCreate;
import com.example.demo.user.mock.FakeMailSender;
import com.example.demo.user.mock.FakeUserRepository;
import com.example.demo.user.mock.TestClockHolder;
import com.example.demo.user.mock.TestUuidHolder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserServiceTest {
    private UserService userService;

    @BeforeEach
    void init() {
        FakeMailSender fakeMailSender = new FakeMailSender();
        FakeUserRepository fakeUserRepository = new FakeUserRepository();
        this.userService = UserService.builder()
                .uuidHolder(new TestUuidHolder("a-a-a"))
                .clockHolder(new TestClockHolder(12309128))
                .userRepository(fakeUserRepository)
                .certificationService(new CertificationService(fakeMailSender))
                .build();
        fakeUserRepository.save(User.builder()
                .id(1L)
                .email("tjsfhdhkd@naver.com")
                .nickname("sunro")
                .address("Seoul")
                .certificationCode("a-a-a")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(0L)
                .build());
    }


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

        //when
        User result = userService.create(userCreateDto);

        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(result.getCertificationCode()).isEqualTo("a-a-a");
    }

    @Test
    @Transactional
    void user를_로그인_시키면_마지막_로그인_시간이_변경된다() {
        //given

        //when
        userService.login(1);
        User user = userService.getById(1);
        System.out.println(user.getLastLoginAt());

        //then
        assertThat(user.getLastLoginAt()).isEqualTo(12309128);
    }
}
