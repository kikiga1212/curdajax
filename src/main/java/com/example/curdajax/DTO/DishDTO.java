package com.example.curdajax.DTO;

import com.example.curdajax.Constant.Category;
import lombok.*;

import java.time.LocalDateTime;

//TODO: HTML에 입력 또는 출력용으로 사용될 변수
//Entity의 모든 변수 사용가능, 필요에 따라서 추가변수 사용가능
//변수에 필요한 메소드 사용가능
@Getter @Setter
@ToString @Builder
@AllArgsConstructor @NoArgsConstructor
public class DishDTO {
    //기본 삽입/수정/삭제/조회에 사용될 기본 Entity변수
    private Integer pid;
    private Category category;
    private String dishname;
    private Integer price;
    private String desc;
    //BaseEntity에 변수
    private LocalDateTime modDate;
    //Constant의 열거형은 상수값이 기본....설명을 사용하려면 추가
    private String cateDesc; //카테코리의 설명을 저장할 변수

    //카테고리 Setter를 사용자 메소드
    //Constant의 값과 설명 저장, 중간계산 후 결과
    public void setCategory(Category category) {
        this.category = category; //상수값
        this.cateDesc = category != null?category.getDescription():null;
    }
}
