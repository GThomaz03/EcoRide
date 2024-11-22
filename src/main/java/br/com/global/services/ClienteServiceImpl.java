package br.com.global.services;

import br.com.global.config.DatabaseConnectionFactory;
import br.com.global.daos.ClienteDao;
import br.com.global.daos.ClienteDaoFactory;
import br.com.global.exceptions.ClienteInvalid;
import br.com.global.exceptions.ClienteNotFound;
import br.com.global.exceptions.ClienteNotSavedException;
import br.com.global.exceptions.ClienteNotUpdatedException;
import br.com.global.models.Cliente;
import br.com.global.services.ValidadorUtil;  // Importando a classe de validadores

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

final class ClienteServiceImpl implements ClienteService {

    private ClienteDao dao = ClienteDaoFactory.create();

    @Override
    public List<Cliente> listarTodos() {
        return this.dao.findAll();
    }

    @Override
    public Cliente create(Cliente cliente) throws ClienteInvalid, SQLException, ClienteNotSavedException {
        if (cliente.getId() != null) {
            throw new ClienteInvalid();
        }

        // Validações
        if (!ValidadorUtil.isCpfValido(cliente.getCpf())) {
            throw new ClienteInvalid();
        }
        if (!ValidadorUtil.isCnhValida(cliente.getCnh())) {
            throw new ClienteInvalid();
        }
        if (!ValidadorUtil.isMaiorDeIdade(cliente.getDataNascimento().toLocalDate())) {
            throw new ClienteInvalid();
        }
        if (!ValidadorUtil.isEmailValido(cliente.getEmail())) {
            throw new ClienteInvalid();
        }

        // Criando o cliente no banco
        Connection conn = DatabaseConnectionFactory.create().get();
        try {
            this.dao.save(cliente, conn);
            conn.commit();
        } catch (SQLException | ClienteNotSavedException e) {
            conn.rollback();
            throw e;
        }
        return cliente;
    }

    @Override
    public Cliente update(Cliente cliente) throws ClienteNotFound, ClienteNotUpdatedException, ClienteInvalid {
        // Validações para update
        if (!ValidadorUtil.isCpfValido(cliente.getCpf())) {
            throw new ClienteInvalid();
        }
        if (!ValidadorUtil.isCnhValida(cliente.getCnh())) {
            throw new ClienteInvalid();
        }
        if (!ValidadorUtil.isMaiorDeIdade(cliente.getDataNascimento().toLocalDate())) {
            throw new ClienteInvalid();
        }
        if (!ValidadorUtil.isEmailValido(cliente.getEmail())) {
            throw new ClienteInvalid();
        }

        // Atualizando cliente no banco
        try (Connection conn = DatabaseConnectionFactory.create().get()) {
            cliente = this.dao.update(cliente, conn);
            conn.commit();
        } catch (SQLException e) {
            throw new ClienteNotUpdatedException();
        }
        return cliente;
    }

    @Override
    public void delete(Long id) throws ClienteNotFound, SQLException {
        Connection connection = DatabaseConnectionFactory.create().get();
        this.dao.deleteById(id, connection);
        connection.commit();
    }
}
