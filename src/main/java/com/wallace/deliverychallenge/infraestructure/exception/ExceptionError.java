package com.wallace.deliverychallenge.infraestructure.exception;

public class ExceptionError extends Exception{
    public ExceptionError(Exception ex) {
        super("Intern Error" + ex.getMessage());
    }
}
