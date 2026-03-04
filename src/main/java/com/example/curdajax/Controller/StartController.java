package com.example.curdajax.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//TODO: 웹 요청을 처리
//TODO: Controller는 GetMapping과 PostMapping만 지원
//GetMapping : localhost:8080?변수=값&변수=값,....(적은 값을 전달할 때)
//PostMapping : localhost:8080  변수와 값은 패킷으로 전달(많은 값을 전달할 때)
@Controller
public class StartController {
    //TODO: 1개의 요청인 경우 ~Mapping("매핑명")
    //TODO: 여러 요청인 경우 ~Mapping({"매핑명", "매핑명})
    //TODO: 요청에 값이 있는 경우 ~Mapping("매핑명/{변수명}")
    @GetMapping("/") //주로 index.html로 이동
    public String index() {
        return "redirect:/dish/list";



        //요청 처리 후 돌아가는 방식은 2가지
        //1. templates의 html로 이동
        //return "경로/html파일명" - 앞에 / 생략
        //2. Mapping을 이용해서 이동
        //return "redirect:/매핑명" - 앞에 /포함
    }
}
