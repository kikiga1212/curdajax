package com.example.curdajax.Repository;

import com.example.curdajax.Entity.DishEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//TODO: 데이터베이스에 접근하는 인터페이스
//TODO: 기본 CRUD를 자동으로 제공
@Repository
public interface DishRepository
        extends JpaRepository<DishEntity, Integer> {
    //기본제공 메소드 :  findAll(), findById(), save(), deleteById()
    //페이지처리, 검색 등은 사용자가 메소드를 만들어서 추가
}
