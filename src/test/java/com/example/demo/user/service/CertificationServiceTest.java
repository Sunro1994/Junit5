package com.example.demo.user.service;

import com.example.demo.user.mock.FakeMailSender;
import com.example.demo.user.service.port.MailSender;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.*;

class CertificationServiceTest {

    @Test
    public void 이메일과_컨텐츠가_제대로_만들어져서_보내지는지_테스트(){

        FakeMailSender fakeMailSender = new FakeMailSender();
        //given
        CertificationService certificationService = new CertificationService(fakeMailSender);

        //when
        certificationService.send("tjsfhdhkd@naver.com",1L,"aaaa-aaa-aa");

        //then
        assertThat(fakeMailSender.email).isEqualTo("tjsfhdhkd@naver.com");
        assertThat(fakeMailSender.title).isEqualTo("Please certify your email address");


    }

}