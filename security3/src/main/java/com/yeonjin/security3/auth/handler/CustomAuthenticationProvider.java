package com.yeonjin.security3.auth.handler;

import com.yeonjin.security3.auth.service.CustomUserDetailService;
import com.yeonjin.security3.auth.service.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.security.sasl.AuthenticationException;
/*
* AuthenticationProvider 커스텀 인증 제공자
* 사용자가 입력한 사용자이름과 비밀번호를 데이터베이스의 정보와 비교하여 사용자 자격을 증명
* */
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;  //암호화된 비밀번호를 확인할때 사용됨,저장된 암호화 사요자입력암호를 비교하는데 사용

    @Autowired
    private CustomUserDetailService detailService;//사용자 아이디로 데이터베이스에서 사용자 정보를 로드하는 역할

    @Override
    public Authentication authentication(Authentication authentication) throws AuthenticationException{

        //로그인 요청 정보를 가지고 잇는 token
        UsernamePasswordAuthenticationToken loginToken = (UsernamePasswordAuthenticationToken) authentication;

        String memberId = loginToken.getName();//사용자가 입력한 아이디를 가져옴

        String password = (String) loginToken.getCredentials(); //사용자가 입력한 비밀번호를 가져옴

        //memberId를 통해 사용자의 정보를 가져옴
        CustomUserDetails member = (CustomUserDetiails) detailService.loadUserByUsername(memberId);

        if(!passwordEncoder.matches(password, member.getPassword())){
            //비밀번호가 일치하지 않으면  BadCredentialsException발생시켜 인증실패처리
            throw new BadCredentialsException(password + "는 비밀번호가 아닙니다");
        }
        //비밀번호가 일치하면 UsernamePasswordAuthenticationToken을 반환하여 인증된 사용자 정보를 설정함
        return new UsernamePasswordAuthenticationToken(member, password, member.getAuthorities());
    }

    /*스프링시큐리티는 여러개의 authenticationprovider를 사용할수있음
    * 각각의 authenticationprovider는 서로다른 인증방식을 처리할수있음,supports는 특정인증요청을 처리할수있는 authenticationprovider을 선택하게함
    * 시스템에 여러 authenticationprovider가 있고 그중 하나가 OAuth2인증을 담당하고 다른하나가 username/password인증을 담당한다고하면
    * supports메서드를 통해 어떤 인증제공자가 현재 인증 요철을 처리할수있는 결정됨*/
    @Override
    //파라미터로 전달된 authentication객체가 customauthenticationprovider가 처리할수있는 타입인지 검사함
    //supports는 customauthenticationprovider가 어떤 타입의 인증을 처리할수있는지명시함, authentication타입과 일치하는지확인
    public boolean supports(Class<?> authentication){
        //UsernamepasswordAuthenticationtoken은 사용자 아이디와 비밀번호를 포함한 인증정보를 나타냄
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
