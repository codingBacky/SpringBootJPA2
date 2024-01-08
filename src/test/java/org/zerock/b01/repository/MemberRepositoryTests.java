package org.zerock.b01.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.zerock.b01.domain.Member;
import org.zerock.b01.domain.MemberRole;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class MemberRepositoryTests {

    @Autowired
    private  MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertMember(){
        IntStream.rangeClosed(1,100).forEach(i->{
            Member member = Member.builder()
                    .mid("member"+i)
                    .mpw(passwordEncoder.encode("1111"))
                    .email("email"+ i+ "@aaa.com")
                    .del(false)
                    .social(false)
                    .build();

            member.addRole(MemberRole.USER);

            if(i>= 90){
                member.addRole(MemberRole.ADMIN);
            }

            memberRepository.save(member);

        });
    }

    @Test
    public void testRead(){
        Member member = memberRepository.getWithRoles("member100").orElseThrow();

        log.info(member);
        log.info(member.getRoleSet());

        member.getRoleSet().forEach(role -> log.info(role));
    }

    @Commit
    @Test
    public void testUpdate(){
        String mid = "back258@nate.com";
        String mpw = passwordEncoder.encode("1234");

        memberRepository.updatePassword(mpw, mid);
    }

}