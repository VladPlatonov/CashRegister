package com.epam.finalproject.dao.exception;

public class ServiceException extends RuntimeException {

    public ServiceException(String massage) {
        super(massage);
    }

    public ServiceException(String massage, Throwable cause) {
        super(massage, cause);
    }
}
