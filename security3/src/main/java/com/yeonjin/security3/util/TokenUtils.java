package com.yeonjin.security3.util;

import io.jsonwebtoken.*;
import lombok.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.Map;

//토큰관리
@Component
public class TokenUtils {

    private static String jwtSecretKey;  //토큰을 서명할떄 사용할 비밀키
    private static Long tokenValidateTime; //토큰이 유효한 시간을 나타내는 변수


    //yml에서 key와 time을 주입받음
    @Value("${jwt.key}")
    public void setJwtSecretKey(String jwtSecretKey){
        TokenUtils.jwtSecretKey = jwtSecretKey;
    }

    @Value("${jwt.time}")
    public void setTokenValidateTime(Long tokenValidateTime){
        TokenUtils.tokenValidateTime = tokenValidateTime;
    }

    //Authorization 헤더에서 토큰을 추출하는메서드, 헤더가 빈값이아니면 공백으로 분리된jwt토큰을 반환함
    public static String splitHeader(String header){
        if(!header.equals("")){
            return header.split("")[1];
        }else {
            return null;
        }
    }

    //유효한 토큰인지 확인하는 메서드, 만료되었거나 변조된 토큰이면, false반환 유효하다면 true를 반환함
    public static boolean isValidToken(String token){
        try {
            Claims claims = getClaimsFromToken(token);
            return true;
        }catch (ExpiredJwtException e){
            e.printStackTrace();
            return false;
        }catch (JwtException e){
            e.printStackTrace();
            return false;
        }catch (NullPointerException e){
            e.printStackTrace();
            return false;
        }
    }

    //토큰을 복호화해서 사용자정보를 추출하는 메서드
    public static Claims getClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(jwtSecretKey))
                .parseClaimsJws(token).getBody();
    }

    //token생성하는 메서드, 사용자 정보를 받아 jwt토큰을 생성하는 메서드 토큰에 만료시간을설정하고
    //사용자 정보를 클레임으로 추가한후 서명(hs256)을 통해 토큰을 생성함
    public static String generateJwtToken(Member member){

        //tokenvalidatetime을 사용하여 토큰이 언제 만료될지 설정함
        //현재시간에서 토큰 유효기간을 더한 값을 expiretime에 저장함
        Date expireTime = new Date(System.currentTimeMillis() + tokenValidateTime);

        JwtBuilder builder = Jwts.builder()

                //jwt헤더부분설정, createheader호출하여alg(알고리즘)과 type(토큰유형)을 포함하는 헤더정보를 반화받아 설정함
                .setHeader(createHeader())

                //jwt클레임부분설정함, 사용자정보(member)가 포함된클래임생성
                .setClaims(createClaims(member))

                //jwt의 주체(subject)부분에 사용자의 이메일을 설정함, 토큰의 소유자가 누군지 나타냄
                .setSubject(member.getMemberEmail())

                //토큰의 만료시간을 설정함
                .setExpiration(expireTime)

                //지정된 알고리즘(hs256)과 서명을 생성하기 위한 비밀키를 사용하여 토큰을 서명함
                .signWith(SignatureAlgorithm.HS256, createSignature());

        //설정된 헤더, 클레임, 서명정보로 최종적으로 jwt토큰을 생성하고 반환함
        return builder.compact();
    }

    //token의 헤더를 설정하는부분
    //헤더에는 토큰타입(jwt), 알고리즘(HS256)발급시간을 포함함
    private static Map<String, Object> createHeader(){
        Map<String, Object> header = new HashMap<>();

        header.put("type", "jwt");
        header.put("alg", "HS256");
        header.put("date", System.currentTimeMillis());

        return header;
    }

    //사용자 정보를 기반으로 클레임을 생성해주는 메서드
    //토큰에 포함될 클레임을 생성하느 메서드, 클레임에는 사용자의 이름, 역할, 이메일이 포함됨
    private static Map<String, Object> createClaims(Member member){

        //클레임정보를 저장할map을 생성함
        Map<String, Object> claims = new HashMap<>();

        //membername이라는 키로 회원의 이름을 클레임에 추가함
        claims.put("memberName", member.getMemberName());
        claims.put("memberRole", member.getRole());
        claims.put("memberEmail", member.getMemberEmail());

        //구성된claims을 반환하여 jwt의 페이로드에 포함시켜 사용할수있음
        return claims;

        //클레임들은 사용자가 인증된후 해당 사용자에 대한 정보를 토큰에 포함시켜 다른서버로 전달하거나, 토큰을 검증할때 사용됨
    }

    //jwt서명을 발급해주는 메서드,서명에는 jwtsecretkey를 사용하며 알고리즘은 hs256임
    private static key createSignature(){
        byte[] secretBytes = DatatypeConverter.parseBase64Binary(jwtSecretKey);
        return new SecretKeySpec(secretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

}
