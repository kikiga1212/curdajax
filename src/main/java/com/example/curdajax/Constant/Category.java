package com.example.curdajax.Constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

//TODO: 상수를 나열해서 이용만
@Getter
@AllArgsConstructor
public enum Category {
    //TODO: 일반적으로 상수값으로만 구성
    //TODO: 상수값("내용",....) 구성시 내용에 해당하는 변수를 선언해야 한다.
    //TODO: 상수값은 데이터베이스에 저장용, "내용"은 HTML에서 출력용
    korean("한식"), western("양식"), japaness("일식"), snack("분식");

    private final String description; //()안에 내용과 1:1 맵핑되도록 선언
}
