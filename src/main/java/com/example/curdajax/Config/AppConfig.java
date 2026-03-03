package com.example.curdajax.Config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//TODO: ModelMapper 자동생성 및 맵핑설정
@Configuration
public class AppConfig {
    //TODO: Springboot에 메소드 등록, 자동으로 생성
    @Bean
    public ModelMapper modelMapper(){
        //TODO: 기본 설정
        //return new ModelMapper();
        //TODO: 사용자가 modelMapper를 설정
        ModelMapper modelMapper = new ModelMapper();
        //TODO: null인 필드는 맵핑시 제외하도록 설정
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        //TODO: DTO와 Entity의 이름과 타입이 일치하는 경우에만 매핑하도록 설정
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return modelMapper;
    }
}
