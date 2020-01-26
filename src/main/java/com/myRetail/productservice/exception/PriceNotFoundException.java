package com.myRetail.productservice.exception;

import org.springframework.http.HttpStatus;

public class PriceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private HttpStatus status;

    public PriceNotFoundException() {
        super();
    }

    public PriceNotFoundException(String message) {
        super(message);
    }

    public PriceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PriceNotFoundException(HttpStatus status, String message){
        super(message);
        this.setStatus(status);

    }

    public PriceNotFoundException(Throwable cause) {
        super(cause);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
