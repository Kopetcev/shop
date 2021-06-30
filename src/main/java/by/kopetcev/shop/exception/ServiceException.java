package by.kopetcev.shop.exception;

import org.springframework.http.HttpStatus;

public class ServiceException extends RuntimeException {

    HttpStatus error;

    public ServiceException() {
    }

    public ServiceException(String message, HttpStatus error) {
        super(message);
        this.error = error;
    }

    public ServiceException(String message, HttpStatus error, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(HttpStatus error, Throwable cause) {
        super(cause);
    }

    public HttpStatus getError() {
        return error;
    }
}