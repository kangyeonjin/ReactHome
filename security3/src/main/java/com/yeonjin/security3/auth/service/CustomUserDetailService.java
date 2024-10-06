package com.yeonjin.security3.auth.service;

import com.yeonjin.security3.user.Entity.Member;
import com.yeonjin.security3.user.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Autowired
    public CustomUserDetailService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Member member = memberRepository.findByMemberId(username)
                .orElseThrow(() -> new AuthenticationServiceException(username + "은 가입되지 않ㅅ았습니다"));
        return new CustomUserDetails(member);
    }
}
