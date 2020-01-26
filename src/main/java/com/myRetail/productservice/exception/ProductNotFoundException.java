package com.myRetail.productservice.exception;

import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private HttpStatus status;

	public ProductNotFoundException() {
        super();
    }

    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ProductNotFoundException(HttpStatus status, String message){
        super(message);
    	this.setStatus(status);
        
    }

    public ProductNotFoundException(Throwable cause) {
        super(cause);
    }

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
}
