package com.yeonjin.security2.member.service;

import com.yeonjin.security2.member.model.dto.SignupDTO;
import com.yeonjin.security2.member.model.entity.Member;
import com.yeonjin.security2.member.model.entity.RoleType;
import com.yeonjin.security2.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor  //final이나@nonnull이 붙은 필드에 대해 자동으로 생성자를 만들어줌
public class MemberService {

    private final MemberRepository memberRepository;  //회원정보관리
    private final PasswordEncoder passwordEncoder;  //비밀번호 인코딩하는데 사용

    //회원가입로직처리함, dto객체에서 전달된 데이터를 사용하여 새로운 회원등록함
    public void register(SignupDTO signupDTO){
        Member member = Member.builder()  //member객체를 빌더패턴으로 생성함
                .memberId(signupDTO.getMemberId())

                //비밀번호 암호화, 암호화된비번을 데이터베이스에 저장함
                .password(passwordEncoder.encode(signupDTO.getPassword()))
                .name(signupDTO.getName())

                //문자열로 전달된 역할(role)을 roletype열거형으로 변환함
                .role(RoleType.valueOf(signupDTO.getRole()))
                .build();

        Member savedMember = memberRepository.save(member);
        log.info("저장된 회원번호 : {}", savedMember.getMemberNo());
    }

    //회원정보 조회 메서드(memberId로 회원을 조회함)
    public Member findMemberById(String memberId){

        Member member = memberRepository.findMemberByMemberId(memberId)

                //회원이 존재하지 않으면 nosuchelementexception던짐
                .orElseThrow(() -> new NoSuchElementException("회원을 찾을수없습니다."));
        return member;
    }

}
