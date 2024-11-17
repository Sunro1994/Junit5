package com.example.demo.medium;

import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.domain.UserEntity;
import com.example.demo.user.infrastructure.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource("classpath:application.properties")
@Sql("/sql/user-repository-test-data.sql")
class UserJpaRepositoryTest {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Test
    void UserRepository_가_제대로_연결되었다() {

        //when
        Optional<UserEntity> result = userJpaRepository.findByIdAndStatus(1L,UserStatus.ACTIVE);

        //then
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    void findByIdAndStatus로_유저_데이터를_찾아올_수_있다(){

        Optional<UserEntity> result = userJpaRepository.findByIdAndStatus(1L,UserStatus.ACTIVE);

        //then
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    void findByIdAndStatus는_데이터가_없으면_Optional_empty_를_내려준다(){

        Optional<UserEntity> result = userJpaRepository.findByIdAndStatus(3,UserStatus.ACTIVE);

        //then
        assertThat(result.isEmpty()).isTrue();

    }

    @Test
    void findByEmailAndStatus로_유저_데이터를_찾아올_수_있다(){

        Optional<UserEntity> result = userJpaRepository.findByEmailAndStatus("tjsfhdhkd@naver.com",UserStatus.ACTIVE);

        //then
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    void findByEmailAndStatus는_데이터가_없으면_Optional_empty_를_내려준다(){

        Optional<UserEntity> result = userJpaRepository.findByEmailAndStatus("Seoul1",UserStatus.ACTIVE);

        //then
        assertThat(result.isEmpty()).isTrue();

    }

}