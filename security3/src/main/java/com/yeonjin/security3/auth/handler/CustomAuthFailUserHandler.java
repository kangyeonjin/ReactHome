package com.yeonjin.security3.auth.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

//로그인과정에서 발생하는 인증실패상황에 대해 적절한 메시지를 json형식으로 클라이언트에게 응답하는 역할을 함
public class CustomAuthFailUserHandler implements AuthenticationFailureHandler {
    @Override
    //로그인 실패시 호출됨, http요청및응답객체를 받아서 처리함, 인증실패시 발생하는 예외에따라 메세지를 처리함
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)throws IOException,SecurityException{
        JSONObject jsonObject;

        String failMsg;

        if(exception instanceof AuthenticationServiceException){
            failMsg = "존재하지 않는 사용자입니다";
        } else if (exception instanceof BadCredentialsException) {
            failMsg = "아이디 또는 비밀번호가 틀립니다";
        } else if (exception instanceof LockedException) {
            failMsg = "잠긴계정입니다";
        } else if (exception instanceof DisabledException) {
            failMsg = "비활성화된 계정입니다";
        } else if (exception instanceof AccountExpiredException) {
            failMsg = "만료된 계정입니다";
        } else if (exception instanceof CredentialsExpiredException) {
            failMsg = "자격증명이 만료되었습니다";
        } else if (exception instanceof AuthenticationCredentialsNotFoundException) {
            failMsg = "인증요청이 거부되었습니다";
        } else if (exception instanceof UsernameNotFoundException) {
            failMsg = "존재하지 않는 사용자입니다";
        } else {
            failMsg = "정의되어있는 케이스의 오류가 아닙니다";
        }

        //예외 메세지가 결정되면 json형식으로 변환해 클라이언트에게 응답함
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();

        HashMap<String, Object> resultMap = new HashMap<>();

        //failtype필드에 실패메세지를 담은 json객체를 생성하여 클라이언트에게 전달함
        resultMap.put("failType", failMsg);

        jsonObject = new JSONObject(resultMap);

        printWriter.println(jsonObject);
        printWriter.flush();
        printWriter.close();
    }
}
