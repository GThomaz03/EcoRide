package br.com.global.daos;

import br.com.global.exceptions.ClienteNotFound;
import br.com.global.exceptions.ClienteNotSavedException;
import br.com.global.models.Cliente;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ClienteDao {
    List<Cliente> findAll();

    void save(Cliente cliente, Connection connection) throws SQLException, ClienteNotSavedException;

    Cliente update(Cliente cliente, Connection connection) throws SQLException, ClienteNotFound;

    void deleteById(Long id, Connection connection) throws ClienteNotFound, SQLException;

}
