package com.example.curdajax.Service;

import com.example.curdajax.DTO.DishDTO;
import com.example.curdajax.Entity.DishEntity;
import com.example.curdajax.Repository.DishRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//TODO: 작업을 진행하는 클래스
//TODO: HTML에서 Controller로 받은 값을 데이터베이스에 처리
//TODO: 데이터베이스에서받은 값을 Controller를 통해서 HTML에 전달
@Service
@RequiredArgsConstructor
@Transactional
public class DishService {
    //TODO: Service에서 사용할 클래스를 주입
    private final DishRepository dishRepository;
    private final ModelMapper modelMapper;

    //TODO: 기본 CRUD구현-
    // 메소드명은 삽입(create, insert, register), 수정(update, modify, edit),
    // 전체조회(list, dataAll), 개별조회(read, find)==>또는 repository의 메소드명과 동일하게
    //전체조회(데이터베이스에 해당데이터 전체를 읽어서 DTO로 Controller에 전달)
    public List<DishDTO> findAll() {
        //데이터베이스->Service 결과는 Entity
        //1. 기본 변환
        List<DishEntity> dishEntities = dishRepository.findAll(); //list, page, 정렬형식으로 읽기가 가능
        //service->Controller 결과는 DTO
        List<DishDTO> dishDTOS = Arrays.asList(
                modelMapper.map(dishEntities, DishDTO[].class)
        );

        //2. stream을 이용한 변환
        List<DishDTO> dishDTOS1 = dishRepository.findAll().stream()
                .map(data->modelMapper.map(data, DishDTO.class)).collect(Collectors.toList());

        //3. modelMapper만 메소드로 분리해서 변환
        List<DishDTO> dishDTOS2 = dishRepository.findAll().stream()
                .map(this::toDTO).collect(Collectors.toList());

        return dishDTOS;

    }
    //TODO: Enity나 DTO를 변환하는 메소드는 Entity, DTO, Service 중에 메소드를 만들어서 사용
    private  DishDTO toDTO(DishEntity entity) {
        return modelMapper.map(entity, DishDTO.class);
    }

    private  DishEntity toEntity(DishDTO dto) {
        return modelMapper.map(dto, DishEntity.class);
    }
    //개별조회(Controller로 부터 조회대상(pid)을 받아서 데이터베이스에 해당데이터를 읽어서 DTO로 Controller에 전달)
    public DishDTO findById(Integer pid) {
        //1. Optional을 이용한 조회
        Optional<DishEntity> dishEntity1 = dishRepository.findById(pid);
        if(dishEntity1.isEmpty()) { //if문으로 존재 여부를 판별할 떄
            throw new IllegalStateException("조회 실패");
        }
        //2. orElse를 이용해서 조회시 오류처리 같이
        DishEntity dishEntity2 = dishRepository.findById(pid).orElse(null);
        if(dishEntity2 == null) {
            throw new IllegalStateException("조회 실패");
        }
        //3. orElseThrow를 이용한 예외처리
        DishEntity dishEntity3 = dishRepository.findById(pid).orElseThrow(()-> new IllegalStateException("조회실패"));

        //변환은 2가지
        //return modelMapper.map(dishEntity3, DishDTO.class);
        return toDTO(dishEntity3);
    }
    //삽입(HTML에서 Controller을 통해서 DTO을 받아서 Entity로 변환후 데이터베이스에 저장)
    public void save(DishDTO dishDTO) {
        //1. 기본변환
        DishEntity dishEntity = modelMapper.map(dishDTO, DishEntity.class);
        //2. 메소드변환
        DishEntity dishEntity2 = toEntity(dishDTO);

        //1. void인 경우 저장
        dishRepository.save(dishEntity);

        //2. public DishEntity인 경우(저장 성공확인)
        //DishEntity result = dishRepository.save(dishEntity);
        //return result;

        //3. public DishDTO인 경우(저장 후 추가 저장작업)
        //DishDTO result = modelMapper.map(dishRepository.save(dishEntity), DishDTO.class);
        //return result;
    }
    //수정(HTML에서 Controller을 통해 수정할 대상, 수정할 DTO를 받아서 Entity로 변환후 데이터베이스에 저장
    //DTO에 PK변수가 존재하는 경우(PK에 값이 존재)-삽입/수정페이지가 다른 경우
    public void modify(DishDTO dishDTO){
        //1. 수정할 내용을 변환(모든 내용을 변경할 때)
        DishEntity dishEntity = modelMapper.map(dishDTO, DishEntity.class);

        //2. 해당 데이터를 조회한 후 필요한 내용만 변경해서 수정
        DishEntity dishEntity1 = dishRepository.findById(dishDTO.getPid()).orElseThrow(
                ()->new IllegalStateException("조회된 데이터 없음")
        );
        //수정할 변수가 많지 않을 때
        dishEntity1.setDishname(dishDTO.getDishname()); //수동으로 변경할 변수만 적용
        dishEntity1.setDesc(dishDTO.getDesc());


        dishRepository.save(dishEntity); //저장종류는 위에 삽입과 동일한 종류로 구성
    }
    //DTO에 PK변수가 존재하지 않는 경우(PK에 값이 존재하지 않는 경우)-삽입/수정페이지를 같이 사용하는 경우
    public void modify2(Integer pid, DishDTO dishDTO){
        DishEntity dishEntity1 = dishRepository.findById(pid).orElseThrow(
                ()->new IllegalStateException("조회된 데이터 없음")
        );
        //수정할 변수가 많은 경우
        dishEntity1.builder()
                .dishname(dishDTO.getDishname())
                .desc(dishDTO.getDesc())
                .price(dishDTO.getPrice())
                .build();
    }


    //삭제(html에서 Controller로 삭제할 대상을 받아서 데이터베이스에서 삭제
    public void deleteById(Integer pid) {
        //삭제는 결과값이 없다. 1. 기본
        dishRepository.deleteById(pid);

        //삭제의 성공여부를 구현 2. public boolean
        //Optional<DishEntity> dishEntity = dishRepository.findById(pid);
        //if(dishEntity.isEmpty()) { 삭제할 대상이 없으면
        //  return false; //삭제실패
        //} else {
        //    dishRepository.deleteById(pid);
        //    return true; //삭제 성공
        //}
    }

    //TODO: 복잡하거나 반복적인 내용, 추가적으로 작업할 내용을 사용자 메소드로 추가

}
