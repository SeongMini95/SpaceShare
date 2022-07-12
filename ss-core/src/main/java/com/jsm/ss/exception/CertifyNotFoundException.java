package com.jsm.ss.exception;

public class CertifyNotFoundException extends RuntimeException {

    public CertifyNotFoundException() {
        super("잘못된 요청입니다.");
    }

    public CertifyNotFoundException(String message) {
        super(message);
    }
}
