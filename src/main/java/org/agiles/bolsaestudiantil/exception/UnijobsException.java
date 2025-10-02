package org.agiles.bolsaestudiantil.exception;

import lombok.Getter;

@Getter
public class UnijobsException extends RuntimeException {
    private String code;

    public UnijobsException(String code, String message) {
        super(message);
        this.code = code;
    }
}
