package com.yeonjin.security3.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
/*API응답데이터를 포멧팅하여 클라이언트에세 전달하는데 사용되는 DTO
* 서버에서 클라이언트오 응답할떄 상태코드, 응답메세지, 응답데이터를 담아 반환하는 역할을 함*/
public class ResponseDTO {

    private int status;  //상태코드값, 성공(200),잘못된요청(400),서버오류(500)등의 상태코드담김
    private String message; //응답메세지, 성공적처리나 오류상황에대한 설명을 포함할수있음
    private Object data;  //클아이언트에세 전달할 실제 데이터로 object타입으로 되있어 다양한 타입으로 전달가능

    public ResponseDTO(){
    }  //매개변수 없는 기본생성자로 특정필드를 초기화하지 않고 객체를 생성할떄 사용됨

    //http객체를 받아 상태코드값을 설정하는 생성자
    public ResponseDTO(HttpStatus status, String message, Object data){
        super();
        this.status =  status.value();  //상태코드를 정수값으로 가져옴
        this.message = message;
        this.data = data;
    }

    @Override
    public String toString(){  //tostring은 객체정보를 문자열로 표현, dto객체가 호출됬을때 status,message,data를 문자열로 확인
        return "ResponseDTO [status=]" + status + ", message=" + message + ", data=" + data + "]";
    }

}
