package me.nightletter.swaggercustom.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static me.nightletter.swaggercustom.config.SwaggerTag.MAIN;
import static org.springframework.http.ResponseEntity.ok;

@Tag(name = "광고")
@RestController
@RequestMapping("/api/v1/advertisements")
public class AdvertisementController {

    @Operation(summary = "광고 - 노출 될 광고 조회", tags = {MAIN})
    @GetMapping
    public ResponseEntity getAdvertisements() {
        return ok().build();
    }
}
