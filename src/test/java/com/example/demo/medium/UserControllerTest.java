package com.example.demo.medium;

import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.domain.request.UserUpdate;
import com.example.demo.user.domain.UserEntity;
import com.example.demo.user.infrastructure.UserJpaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SqlGroup({
        @Sql(value = "/sql/user-controller-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class UserControllerTest {

    //mockMVC를 사용하는데 api테스트에 자주 사용된다.
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserJpaRepository userJpaRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void 헬스_체크_응답이_200으로_내려온다() throws Exception {
        mockMvc.perform(get("/health_check.html"))
                .andExpect(status().isOk());
    }

    @Test
    void 사용자는_특정_유저의_정보를_전달_받을_수_있다() throws Exception {
        //given
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("tjsfhdhkd@naver.com"))
                .andExpect(jsonPath("$.nickname").value("sunro"));
        //when
        //then

        mockMvc.perform(get("/health_check.html"))
                .andExpect(status().isOk());
    }

    @Test
    void 사용자는_존재하지않는_유저의_정보를_전달_받은_경우_404_응답을받는다() throws Exception {
        //given
        mockMvc.perform(get("/api/users/12"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Users에서 ID 12를 찾을 수 없습니다."));
    }

    @Test
    void 사용자는_인증_코드로_계정을_활성화_시킬_수_있다() throws Exception {
        mockMvc.perform(
                        get("/api/users/2/verify")
                                .queryParam("certificationCode", "a0aa0aaaa-a-aaaaa-aa"))
                .andExpect(status().isFound());
        UserEntity userEntity = userJpaRepository.findById(1L).get();

        Assertions.assertThat(userEntity.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void 사용자는_내_정보를_불러올_때_기본정보인_주소도_갖고_올_수_있다() throws Exception {
        mockMvc.perform(
                        get("/api/users/me")
                                .header("EMAIL", "tjsfhdhkd@naver.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("tjsfhdhkd@naver.com"));

    }



    @Test
    void 사용자는_내_정보를_수정할_수_있다() throws Exception {
        //given
        UserUpdate userUpdate = UserUpdate.builder()
                .nickname("sunro123")
                .address("Pangyo")
                .build();

        mockMvc.perform(
                        put("/api/users/me")
                                .header("EMAIL", "tjsfhdhkd@naver.com")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("tjsfhdhkd@naver.com"))
                .andExpect(jsonPath("$.nickname").value("sunro123"))
                .andExpect(jsonPath("$.address").value("Pangyo"));
    }


}