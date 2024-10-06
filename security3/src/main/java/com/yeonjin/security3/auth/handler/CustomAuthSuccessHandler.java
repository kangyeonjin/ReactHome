package com.yeonjin.security3.auth.handler;

import com.yeonjin.security3.auth.service.CustomUserDetails;
import com.yeonjin.security3.common.AuthConstants;
import com.yeonjin.security3.user.Entity.Member;
import com.yeonjin.security3.util.ConvertUtil;
import com.yeonjin.security3.util.TokenUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.filters.ExpiresFilter;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.json.simple.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Objects;

@Configuration
public class CustomAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        Member member = ((CustomUserDetails) authentication.getPrincipal()).getMember();

        JSONObject jsonValue = (JSONObject) ConvertUtil.convertObjectToJsonObject(member);

        HashMap<String, Objects> responseMap = new HashMap<>();

        responseMap.put("userInfo", jsonValue);
        responseMap.put("message", "로그인성공");

        String token = TokenUtils.generateJwtToken(member);

        response.addHeader(AuthConstants.AUTH_HEADER, AuthConstants.TOKEN_TYPE+""+token);

        JSONObject jsonObject = new JSONObject(responseMap);

        response.setCharacterEncoding("UTF-8");

        response.setContentType("application/json");

        PrintWriter printWriter = response.getWriter();
        printWriter.println(jsonObject);
        printWriter.flush();
        printWriter.close();

    }


}
