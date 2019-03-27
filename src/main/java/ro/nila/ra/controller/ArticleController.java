package ro.nila.ra.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.nila.ra.payload.ApiResponse;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @GetMapping
    public ResponseEntity sayHello(){

        return ResponseEntity
                .ok(new ApiResponse(true, "working",null));
    }

}
