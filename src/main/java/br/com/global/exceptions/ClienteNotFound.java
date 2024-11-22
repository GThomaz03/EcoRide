package br.com.global.exceptions;

public class ClienteNotFound extends Exception {

    public ClienteNotFound(Long id) {
        super("nao localizado -> id: "+id);
    }
}
