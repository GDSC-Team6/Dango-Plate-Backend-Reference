package com.example.dango.global.exception;

public class ServerErrorException extends RuntimeException {

    public ServerErrorException() {
        super("서버 내부 에러입니다.");
    }

    public ServerErrorException(String s) {
    }
}

