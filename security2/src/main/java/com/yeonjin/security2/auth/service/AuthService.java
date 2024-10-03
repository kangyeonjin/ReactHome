package com.yeonjin.security2.auth.service;

import com.yeonjin.security2.member.model.entity.Member;
import com.yeonjin.security2.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.yeonjin.security2.auth.principal.AuthPrincipal;

@Service
@RequiredArgsConstructor  //final로 선언된 필드를 포함하는 생성자를 자동으로 생성함
@Slf4j
@Transactional  //이 클래스의 메소드들이 트랜잭션안에서 실행됨을 보장해줌
public class AuthService implements UserDetailsService {

    private final MemberRepository memberRepository;
    
    //회원정보를 조회하며, 일치하는지 확인하는 로직, 일치하면 userdetails객체를 반환해줌
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        Member member = memberRepository.findMemberByMemberId(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        log.info("로그인하는 회원이름 : {}", member.getName());
        
        return new AuthPrincipal(member);
    }

}
