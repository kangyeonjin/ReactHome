package com.yeonjin.security3.user.Entity;

/*enum(열거형) :특정값들의 집합을 정의하고, 각 값에 대해 고유한 의미를 부여하는데 사용됨
* 여기서는 사용자의 역할을 나타내기 위해 정의되었음, user,admin두가지 역할임*/
public enum Role {
    USER("USER"),
    ADMIN("ADMIN");

    //각역할에 대한 문자열 값을 저장하는 필드임, user, admin이저장됨
    private String role;

    //role객체를 생성할떄 역할에 해당하는 문자열 값을 전달받아 role필드에 저장함
    Role(String role) {
        this.role = role;
    }

    //getrole메서드는 role객체의 role값을 반환함
    public String getRole(){
        return role;
    }


}
