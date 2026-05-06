package me.nightletter.swaggercustom.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static me.nightletter.swaggercustom.config.SwaggerTag.MAIN;
import static org.springframework.http.ResponseEntity.ok;

@Tag(name = "알림")
@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    @Operation(summary = "알림 - 알림 내역 조회")
    @GetMapping
    public ResponseEntity getNotifications() {
        return ok().build();
    }

    @GetMapping("/count-unread")
    public ResponseEntity getUnreadCount() {
        return ok().build();
    }

    @Operation(summary = "알림 - 읽지 않은 알림 존재여부 조회", tags = {MAIN})
    @GetMapping("/exist-unread")
    public ResponseEntity getExistUnread() {
        return ok().build();
    }
}
