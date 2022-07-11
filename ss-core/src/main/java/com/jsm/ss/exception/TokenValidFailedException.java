package com.jsm.ss.exception;

public class TokenValidFailedException extends RuntimeException {

    public TokenValidFailedException() {
        super("token 검증 실패");
    }

    public TokenValidFailedException(String message) {
        super(message);
    }
}
