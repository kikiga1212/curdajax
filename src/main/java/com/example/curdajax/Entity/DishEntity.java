package com.example.curdajax.Entity;

import com.example.curdajax.Constant.Category;
import jakarta.persistence.*;
import lombok.*;

//TODO: 데이터베이스에 실질적으로 저장할 테이블
@Entity
@Table(name="dish")
@Getter @Setter
//TODO: 여러 테이블을 연관(OneToMany, ManyToOne) 사용시 부모테이블 또는 자식테이블을 생략
//@ToString(exclude = 생략할 필드명)
//TODO: 하나의 테이블로 작업할 때
@ToString
@Builder
@AllArgsConstructor @NoArgsConstructor
public class DishEntity extends BaseEntity {
    //TODO: 반드시 기본키(PK)가 존재
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pid; //id, pid, no등을 사용
    //TODO: name이 생략이 되면 private 선언된 이름과 동일한 이름이 적용
    //Category로 대문자가 있는 필드는 데이터베이스에서 _category 밑줄 소문자로 변환되어서 적용
    @Column(name="category", nullable = false)
    @Enumerated(EnumType.STRING) //열거형에 상수값으로 저장
    private Category category;  //음식분류
    @Column(name="dishname", length=200, nullable=false)
    private String dishname;    //음식명
    @Column(name="price")
    private Integer price;      //가격
    @Column(name="desc", length=200)
    private String desc;        //설명

    //Entity와 DTO에 변환 메소드를 사용할 때는 builder를 이용해서 각 변수별로 값을 변환해서 사용
}
