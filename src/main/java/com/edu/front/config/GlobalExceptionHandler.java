package com.edu.front.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

import javax.naming.SizeLimitExceededException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = FileNotFoundException.class)
    public void handle(FileNotFoundException ex, HttpServletResponse response) throws IOException {
        log.warn("File not found: {}", ex.getMessage());
        response.sendError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(value = IOException.class)
    public void handle(IOException ex, HttpServletResponse response) throws IOException {
        log.error("IO exception occurred", ex);
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }

    @ExceptionHandler({ SizeLimitExceededException.class, MultipartException.class,
            java.lang.IllegalStateException.class })
    public void handleFileSizeError(HttpServletRequest req, Exception ex, HttpServletResponse response) throws IOException {
        log.warn("File upload error on [{}]: {}", req.getRequestURL(), ex.getMessage());
        response.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public void handle(Exception ex, HttpServletResponse response) throws IOException {
        log.error("Unhandled exception occurred", ex);
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }

}
