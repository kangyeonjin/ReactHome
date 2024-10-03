package com.yeonjin.security.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.swing.plaf.PanelUI;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    //특정경로를 보안필터링에서 제외(보안필터를 거치지않고 바로 접근가능하게 해줌)
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring().requestMatchers("/css/**", "/js/**", "/images/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
      //authorizehttprequests : 어떤보안정책을 적용할지(들어오는 http 요청에대해 어떤 요청이 인증이나 권한검사를 거쳐야하는지설정함)
        http.authorizeHttpRequests((registry)->{

            //루트경로와 index.html파일에 대한 요청을 매칭함, permitAll은 누구나 접근가능
            registry.requestMatchers("/", "/index.html").permitAll()
                    //board아래의 모든 경로에 대해 매칭함, 하위경로포함, authenticated :사용자가 인증된 상태여야 접근가능함
                    .requestMatchers("/board/**").authenticated()
                    //admin아래의 모든경로를매칭함, admin역할을 가진사용자만 접근가능함
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    //위에서 명시적으로 설정하지 않은 모든 요청을 의미함,나머지경로는 로그인한 사용자만 접근하게 authenticated함
                    .anyRequest().authenticated();
//            registry.anyRequest().permitAll();
        });

        //로그인설정
        //폼기반 로그인방식활성화, 로그인폼을 처리하는 필터를 추가하고 로그인 관련 동작을 정의함
        http.formLogin(configurer->{

            //사용자가 로그인할떄 보여줄 커스텀 로그인페이지 경로를 지정함
            configurer.loginPage("/auth/login")
                    //실제 로그인 요청을 처리할 url경로를 지정함,사용자가 로그인폼에서 입력한 정보를 정송할떄 이경로로post요청이 전송됨
                    .loginProcessingUrl("/auth/login")

                    //로그인폼에서 사용자 이름을 입력하는 필드의 파라미터 이름을 지정함
                    .usernameParameter("username")
                    //비밀번호 입력하는필드의 파라미터이름지정
                    .passwordParameter("password")
                    //누구나 이경로에 접근할수있게함
                    .permitAll();
        });

        //로그아웃설정
        http.logout(configurer->{
            configurer.logoutUrl("/auth/logout")
                    .logoutSuccessUrl("/auth/login");
        });

        /*http.build는 httpsecurity객체의 설정을 기반으로 보안필터 체인을 생성
        *애플리케이션에 대한 보안설정을 정의하는데 사용됨,
        * 로그인인증, 권한부여, 세션관리, csrf방지등의 보안관련설정이 포함됨
        * 설정이 완료돈후 build메서드를 호출하여 securityfilterchain이라는 객체를 생성함
        * 실제로 요청이 들어올떄마다 스프링시큐리티가 적용할 필터들의 체인을 나타냄 */

        /*시큐리티 필터체인
        * 요청을 처리할떄 적용되는 보안필터들의 집합임, 사용자가 요청을 보낼떄 필터체인이 순차적으로 실행되어 보안규칙을 적용
        * 여러개의 필터를 포함하며, 각 필터는 인증, 권한검사, 세션관리등의 역할을 담당함*/

        //모든 보안설정이 httpsecurity를 통해 구성된후, build를 호출하여 그 설정을 기반으로 보안 필터체인이 완성됨
        //이 필터체인은스프링 시큐리티가 애플리케이션에 대해 http요청을 처리할떄 사용됨
        //스프링시큐리티에 필요한 보안규칙들을 적용한후 이를 실행가능한 필터체인으로 변환화여반환함
        return http.build(); //보안설정을 마무리하고 설정을 기반으로 시큐리티필터체인을 생성하여 반환하는 메서드임
                             //반환된 필터체인객체는 http요청을 처리할떄 보안필터로 작동하게 됨
    }

    @Bean  //객체등록
    //passwordencoder는 비밀번호를 암호화하거나 암호화된 비밀번호를 검증할떄 사용됨
    public PasswordEncoder passwordEncoder(){
        //bcrypt해싱 알고리즘을 사용하여 비밀번호를 암호화함(bcrypt는 단방향해시함수임, 복호화가안됨)
        //bcrypt는 사용자가 입력한 비밀번호를 동일한 알고리즘으로 해시처리한뒤,
        //기존의 해시값과 비교하는 방식으로 비밀번호검증이 이루어짐 salt라는 임의의 데이터를 추가해서 같은 비밀번호라도다른결과를 만들어냄
        return new BCryptPasswordEncoder();
    }

    @Bean
    //사용자정보를 조회하고 인증을 처리하는데 필요한 정보(권한)를 제공하기위함
    public UserDetailsService userDetailsService(){

        //일반사용자
        //userdetails는 사용자 정보를 표현함, 여기서는 이름, 비번, 역할(roles)를 가지고있음
        UserDetails user = User.builder()
                .username("user01")
                .password(passwordEncoder().encode("1234"))
                .roles("USER")
                .build();

        //관리자
        UserDetails admin = User.builder()
                .username("hihi")
                .password(passwordEncoder().encode("1234"))
                .roles("USER", "ADMIN")
                .build();

        //inmemoryuserdetailsmanager는 메모리내에서 사용자정보를 저장하고 관리하는객체임
        //데이터베이스나 외부저장소가아닌 메모리상에서 사용자 정보를 저장하고 이를 기반으로 인증을 처리함
        return new InMemoryUserDetailsManager(user, admin);//user,admin이라는 사용자생성후 메모리내에 저장하여 관리할수있도록 InMemoryUserDetailsManager에넘겨줌
    }
}
