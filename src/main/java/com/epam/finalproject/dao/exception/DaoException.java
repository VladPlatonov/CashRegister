package com.epam.finalproject.dao.exception;

public class DaoException extends RuntimeException {

    public DaoException(String massage) {
        super(massage);
    }

    public DaoException(String massage, Throwable cause) {
        super(massage, cause);
    }
}
