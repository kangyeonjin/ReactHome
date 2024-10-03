package com.yeonjin.security3.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//자바객체를 json객체로 변환하는 유틸리티 클래스임
//objectMapper와 jsonparser를 사용하여 자바 객체를 json문자열로 직렬화하고
//json문자열을 다시 json객체로 파싱하는 작업을 수행함
public class ConvertUtil {

    //입력으로 전달된 자바객체obj를 joon객체로 변환하여 반환함
    public static Object convertObjectToJsonObject(Object obj){

        //objectmapper : jackson라이브러리에서 제공하는 클래스로, 자바객체를 json으로 직렬화하거나 json을 자바객체로 역직렬화하는데 사용됨
        ObjectMapper mapper = new ObjectMapper();

        //jsonpaser : json파싱을 담당하는클래스, 문자열로 표현된 json데이터를 json객체로 변환함
        JSONParser parser = new JSONParser();

        String convertJsonString; //자바객체를 json문자열로 변환한 결과를 담을 변수
        Object convertObj; //변환된 json객체를 저장하는변수

        try{

            //objectmapper를 사용하여 자바객체를 json문자열로 반환함
            convertJsonString = mapper.writeValueAsString(obj);

            //변환된 json문자열을 다시 json객체로 파싱함
            convertObj = parser.parse(convertJsonString);

            //자바객체를 json문자열로 변환하는과정에서 발생할수있는 예외처리
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);

            //json문자열을 파싱하는 과정에서 발생할수있는 예외처리
        }catch (ParseException e){
            throw new RuntimeException(e);
        }

        //변환된 json객체를 반환함
        return convertObj;
    }

}
