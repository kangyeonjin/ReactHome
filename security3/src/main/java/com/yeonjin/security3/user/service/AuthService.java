package com.yeonjin.security3.user.service;

import com.yeonjin.security3.user.DTO.MemberDTO;
import com.yeonjin.security3.user.Entity.Member;
import com.yeonjin.security3.user.Entity.Role;
import com.yeonjin.security3.user.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository userRepository;

    @Autowired
    public AuthService(ModelMapper modelMapper, PasswordEncoder passwordEncoder, MemberRepository userRepository) {
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Transactional
    public MemberDTO signup(MemberDTO memberDTO){

        //비밀번호 암호화
        memberDTO.setMemberPass(passwordEncoder.encode(memberDTO.getMemberPass()));

        memberDTO.setRole(Role.USER);

        //데이터베이스에 저장하기 위해 dto에 담긴값을 entity로 변경
        Member registMember = modelMapper.map(memberDTO, Member.class);

        //저장
        Member savedMember = userRepository.save(registMember);

        MemberDTO responseMemberDTO = modelMapper.map(savedMember, MemberDTO.class);

        return responseMemberDTO;
    }

}
