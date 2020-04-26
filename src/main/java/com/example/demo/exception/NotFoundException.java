package com.example.demo.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super("리소스를 찾지 못했습니다.");
    }
}
