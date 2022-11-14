package com.ejercicio.apirest.excepcion;

public class ClienteNotFoundException extends Exception {
    public ClienteNotFoundException(String message) {
        super(message);
    }
}
