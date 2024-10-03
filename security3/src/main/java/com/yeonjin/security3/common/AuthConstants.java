package com.yeonjin.security3.common;

/*애플리케이션에서 인증과 관련된 상수값을 정의함
* 인증에 사용되는 헤더 정보와 토큰 유형을 상수로 저장하여, 다른클래스에서 반복적으로 사용되는 값을 코트내에서
* 하드코딩하지 않고 상수를 통해 관리하는 역할을함 */

public class AuthConstants {

    //auth_header : http요청에서 인증정보를 담고있는 헤더의 이름
    //보통 authorization으로 정희함, jwt나 oauth같은 토큰기반 인증시스템에서 이헤더 사용함
    public static final String AUTH_HEADER = "Authorization";

    //사용되는 토큰 유형을 나타냄
    //bearer는 토큰앞에 붙여서 토큰타입을 지정하는 역할을 함
    //토큰을 사용하는 모든 요청의 authorization헤더에서 사용됨
    public static final String TOKEN_TYPE = "Bearer";

}
