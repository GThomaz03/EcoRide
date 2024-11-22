package br.com.global.services;

import br.com.global.exceptions.ClienteInvalid;
import br.com.global.exceptions.ClienteNotFound;
import br.com.global.exceptions.ClienteNotSavedException;
import br.com.global.exceptions.ClienteNotUpdatedException;
import br.com.global.models.Cliente;

import java.sql.SQLException;
import java.util.List;

public interface ClienteService {

    List<Cliente> listarTodos();

    Cliente create(Cliente cliente) throws ClienteInvalid, SQLException, ClienteNotSavedException;

    Cliente update(Cliente cliente) throws ClienteNotFound, ClienteNotUpdatedException, ClienteInvalid;

    void delete(Long id) throws ClienteNotFound, SQLException;

}
