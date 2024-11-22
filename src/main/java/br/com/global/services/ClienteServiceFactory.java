package br.com.global.services;

public final class ClienteServiceFactory {

    private ClienteServiceFactory() {
        throw new UnsupportedOperationException("classe factory");
    }

    public static ClienteService create(){
        return new ClienteServiceImpl();
    }
}
