package com.yeonjin.security2.auth.principal;

import com.yeonjin.security2.member.model.entity.Member;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Data
//사용자 인증처리할떄 userdetails인터페이스 구현해서 사용자 정보를 제공함
//userdetails는 스프링시큐리티에서 사용자를 나타내는 인터페이스임, 인증시스템에서
//사용자 정보의 관리를 위한 메소드를 제공함
//serializable은 직렬화 가능하도록 만들어줌
public class AuthPrincipal implements UserDetails, Serializable {

    //멤버객체를 인스턴스변수로 가지며 회원정보를 담고있음
    private final Member member;

    @Override
    //사용자의 권한을 반화하는 메소드임
    //스프링시큐리티는 역할을 grantedauthority라는 객체로 표현하며 권한에 대한 리스트를 반환해야함
    public Collection<? extends GrantedAuthority> getAuthorities(){
        ArrayList<GrantedAuthority> auth = new ArrayList<>();

        //회원의 역할에 role_을 붙여서 simplegrantdauthority객체로 변환함
        //member.getrole이 user라면 role_user라는 권한이 추가됨
        auth.add(new SimpleGrantedAuthority("ROLE_"+ member.getRole()));
        return auth;
    }

    @Override
    public String getPassword(){
        return member.getPassword();
    }

    @Override
    public String getUsername(){
        return member.getMemberId();
    }


    //계정 상태관련 메소드
    //계정만료여부
    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    //게정잠김여부
    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    //자격증명만료여부
    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    //계정활성화여부
    @Override
    public boolean isEnabled(){
        return true;
    }

}
