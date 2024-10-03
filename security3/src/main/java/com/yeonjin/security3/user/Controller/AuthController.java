package com.yeonjin.security3.user.Controller;

import com.yeonjin.security3.common.ResponseDTO;
import com.yeonjin.security3.user.DTO.MemberDTO;
import com.yeonjin.security3.user.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseDTO> signup(@RequestBody MemberDTO memberDTO){
        System.out.println(memberDTO);
        MemberDTO savedMemberDTO = authService.signup(memberDTO);

        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.CREATED), "회원가입 성공", savedMemberDTO);
    }
}
