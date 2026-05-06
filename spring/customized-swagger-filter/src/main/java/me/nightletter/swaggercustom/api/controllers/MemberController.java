package me.nightletter.swaggercustom.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static me.nightletter.swaggercustom.config.SwaggerTag.MAIN;
import static org.springframework.http.ResponseEntity.ok;

@Tag(name = "회원")
@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

    @Operation(summary = "회원 - 상세")
    @GetMapping("/{id}")
    public ResponseEntity getMember() {
        return ok().build();
    }

    @Operation(summary = "회원 - 닉네임 조회", tags = {MAIN})
    @GetMapping("/{id}/nickname")
    public ResponseEntity getNickname() {
        return ok().build();
    }
}
