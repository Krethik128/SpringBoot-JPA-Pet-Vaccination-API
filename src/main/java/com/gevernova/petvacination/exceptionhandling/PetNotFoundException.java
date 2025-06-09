package com.gevernova.petvacination.exceptionhandling;

public class PetNotFoundException extends RuntimeException {
    public PetNotFoundException(String message) {
        super(message);
    }
}
