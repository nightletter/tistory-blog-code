package me.nightletter.swaggercustom.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static me.nightletter.swaggercustom.config.SwaggerTag.MAIN;
import static org.springframework.http.ResponseEntity.ok;

@Tag(name = "계좌")
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    @Operation(summary = "계좌 - 계좌 전체 조회", tags = {MAIN})
    @GetMapping
    public ResponseEntity getAccounts() {
        return ok().build();
    }
}
