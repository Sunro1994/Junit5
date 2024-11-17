package com.example.demo.medium;

import com.example.demo.user.domain.request.UserCreate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SqlGroup(
        @Sql(value = "/sql/delete-all.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
)
class UserCreateControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @MockBean
    private JavaMailSender mailSender;

    @Test
    void 사용자는_내_정보를_수정할_수_있다() throws Exception {
        //given
        UserCreate userCreateDto = UserCreate.builder()
                .email("test@naver.com")
                .nickname("sunro123")
                .address("Pangyo")
                .build();
        BDDMockito.doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        mockMvc.perform(
                        post("/api/users")
                                .header("EMAIL", "tjsfhdhkd@naver.com")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.email").value("test@naver.com"))
                .andExpect(jsonPath("$.nickname").value("sunro123"))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }



}