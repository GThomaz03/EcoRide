package br.com.global.daos;

public final class ClienteDaoFactory {

    private ClienteDaoFactory() {
    }

    public static ClienteDao create(){
        return new ClienteDaoImpl();
    }
}
