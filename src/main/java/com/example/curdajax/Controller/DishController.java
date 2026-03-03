package com.example.curdajax.Controller;

//Controller와 ResponseBody를 이용해서 Rest(비동기식)방식으로 처리

import com.example.curdajax.Constant.Category;
import com.example.curdajax.DTO.DishDTO;
import com.example.curdajax.Service.DishService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
//TODO: 요청시 /dish포함하고 그 뒤에 작업 맵핑명
@RequestMapping("/dish") //공통매핑 지정(Entity별로 구분)
public class DishController {
    private final DishService dishService;

    //TODO: Controller방식은 목록, 상세보기, 삽입폼, 수정폼  : Controller->HTML
    // return으로 이동할 페이지 지정, Model을 이용해서 값을 전달
    // 일반적으로 try~catch없이 사용(필요에 따라 if문으로 )
    // return을 통해서 페이지를 다시 읽어서 브라우저에 출력을 다시 한다.->화면이 깜박이는 현상이 발생

    //TODO: ResponseBody(비동기식)은 삽입처리, 수정처리, 삭제처리 : Controller->Service
    // return으로 값을 전달, 페이지는 지정(X)하지 않고 요청한 페이지로 이동
    // 일반적으로 try~catch를 이용해서 성공, 실패에 따른 메세지를 전달
    // 브라우저의 내용은 변경하지 않고 이동만 한다.->화면 갱신이 필요하면 javascript로 갱신작업

    //목록페이지
    @GetMapping("/list")
    public String ListForm(Model model) {
        List<DishDTO> dishDTOS = dishService.findAll();
        model.addAttribute("dishDTOS", dishDTOS);
        return "list";
    }
    //상세페이지
    //1. localhost:8080/dish/read/1 ==> react방식(비동기식)
    //@GetMapping("/read/{pid}")
    //public String readForm(@PathVariable Integer pid, Model model)

    //2. localhost:8080/dish/read?pid=1 ==>spring, springboot방식(동기식)
    @GetMapping("/read")
    public String readForm(@RequestParam Integer pid, Model model) {
        DishDTO dto = dishService.findById(pid);
        model.addAttribute("dishDTO", dto);
        return "read";
    }
    //삽입폼
    @GetMapping("/register")
    public String saveForm(Model model) {
        model.addAttribute("types", Category.values()); //열거형값을 전달
        //object로 form을 구현할 때는 빈 DTO를 전달
        //빈 DTO를 전달하면 object를 이용해서 form을 단순화, 검증처리
        model.addAttribute("dishDTO", new DishDTO());

        return "register";
    }
    //삽입처리
    @PostMapping("/register")
    @ResponseBody //비동기식으로 처리
    public ResponseEntity<String> save(@ModelAttribute DishDTO dishDTO) {
        try { //저장 성공시
            dishService.save(dishDTO);
            return ResponseEntity.ok("저장하였습니다."); //<String>선언시 ok()안에는 문자열
        } catch(Exception e) { //저장 실패시
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("저장을 실패하였습니다."); //status 오류메세지의 종류를 지정 body 문자열
        }
    }
    //수정폼
    @GetMapping("/modify")
    public String modify(Integer pid, Model model) {
        DishDTO dishDTO = dishService.findById(pid);
        model.addAttribute("dishDTO", dishDTO);
        model.addAttribute("types", Category.values());

        return "modify";
    }
    //수정처리
    @PostMapping("/modify")
    public ResponseEntity<String> modifyProc(@ModelAttribute DishDTO dishDTO) {
        try {
            dishService.modify(dishDTO);
            return ResponseEntity.ok("수정하였습니다.");
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("수정을 실패하였습니다.");
        }
    }
    //삭제처리
    @GetMapping("/remove")
    @ResponseBody
    public ResponseEntity<String> remove(Integer pid) {
        try {
            dishService.deleteById(pid);
            return ResponseEntity.ok("삭제하였습니다.");
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제를 실패하였습니다.");
        }
    }
}

//TODO: 주요 HttpStatus의 값
// 200 OK : 성공
// 204 No COntent : 성공했지만 전달값 없음
// 400 Bad Request : 잘못된 요청
// 401 Unauthorized : 인증 실패
// 403 Forbidden : 접근 금지
// 404 Not Found : 자원 없음(파일이 없음)
// 500 Internal Server Error : 서버 내부 오류(일반적인 작업 오류)