package me.nightletter.playground;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.EventListener;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class ExHandler {

    @ExceptionHandler(RuntimeException.class)
    public void handler(RuntimeException ex) {
        log.error(ex.getMessage(), ex);
    }

    @ExceptionHandler(Error.class)
    public void handlerError(Error ex) {
        log.error(ex.getMessage(), ex);
    }
}
