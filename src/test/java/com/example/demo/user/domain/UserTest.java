package com.example.demo.user.domain;

import com.example.demo.common.domain.exception.CertificationCodeNotMatchedException;
import com.example.demo.user.domain.request.UserCreate;
import com.example.demo.user.domain.request.UserUpdate;
import com.example.demo.user.mock.TestClockHolder;
import com.example.demo.user.mock.TestUuidHolder;
import com.example.demo.user.service.port.ClockHolder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserTest {


    @Test
    public void UserCreate객체로_생성할_수_있다() {
        //given
        UserCreate createUser = UserCreate.builder()
                .email("tjsfhdhkd@naver.com")
                .nickname("tester")
                .address("Gyeongi")
                .build();
        //when
        User user = User.from(createUser, new TestUuidHolder("aaaa"));

        //then
        assertThat(user.getId()).isNull();
        assertThat(user.getEmail()).isEqualTo("tjsfhdhkd@naver.com");
        assertThat(user.getNickname()).isEqualTo("tester");
        assertThat(user.getAddress()).isEqualTo("Gyeongi");
        assertThat(user.getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(user.getCertificationCode()).isEqualTo("aaaa");
    }

    @Test
    public void UserUpdate_객체로_업데이트_할_수_있다() {
        //given
        UserUpdate userUpdate = UserUpdate.builder()
                .nickname("test-t")
                .address("GWANGJU")
                .build();

        User user = User.builder()
                .id(1L)
                .email("tjsfhdhkd@naver.com")
                .nickname("tester")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("a-a-a")
                .build();
        //when
        user = user.update(userUpdate);

        //then
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getEmail()).isEqualTo("tjsfhdhkd@naver.com");
        assertThat(user.getNickname()).isEqualTo("test-t");
        assertThat(user.getAddress()).isEqualTo("GWANGJU");
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(user.getCertificationCode()).isEqualTo("a-a-a");

    }

    @Test
    public void 로그인을_할_수_있고_로그인시_마지막_로그인_시간이_변경된다() {
        //given
        User user = User.builder()
                .id(1L)
                .email("tjsfhdhkd@naver.com")
                .nickname("tester")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("a-a-a")
                .build();

        //when
        user = user.login(new TestClockHolder(1672817281));

        //then
        assertThat(user.getLastLoginAt()).isEqualTo(1672817281);
    }

    @Test
    public void 유효한_인증_코드로_계정을_활성화_할_수_있다() {
        //given
        User user = User.builder()
                .id(1L)
                .email("tjsfhdhkd@naver.com")
                .nickname("tester")
                .address("Seoul")
                .status(UserStatus.PENDING)
                .certificationCode("a-a-a")
                .build();
        //when
        user = user.certificate("a-a-a");
        //then
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    public void 잘못된_인증_코드로_계정을_활성화_하려면_에러를_던진다() {
        //given
        User user = User.builder()
                .id(1L)
                .email("tjsfhdhkd@naver.com")
                .nickname("tester")
                .address("Seoul")
                .status(UserStatus.PENDING)
                .certificationCode("a-a-a")
                .build();
        //when
        //then
        assertThatThrownBy(() -> user.certificate("aaaa"))
                .isInstanceOf(CertificationCodeNotMatchedException.class);
    }

}