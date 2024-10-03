package com.yeonjin.security2.member.controller;

import com.yeonjin.security2.member.model.dto.SignupDTO;
import com.yeonjin.security2.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/register")
    public String register(){return "member/signup";}

    @PostMapping("/register")
    public String register(SignupDTO signupDTO){
        log.info("signup :{}", signupDTO);
        memberService.register(signupDTO);
        return "redirect:/";
    }

}
