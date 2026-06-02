package me.nightletter.playground.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ValidController {

    @GetMapping
    public ResponseEntity test(
            @ModelAttribute @Valid TestRequest testRequest
    ) {
        if (1!=2) {
//            throw new RuntimeException();
            throw new AssertionError();
        }
        return ResponseEntity.ok()
                .build();
    }
}
