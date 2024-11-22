package br.com.global.daos;

import br.com.global.config.DatabaseConnectionFactory;
import br.com.global.exceptions.ClienteNotFound;
import br.com.global.exceptions.ClienteNotSavedException;
import br.com.global.models.Cliente;
import br.com.global.models.Plano;
import oracle.jdbc.OracleType;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ClienteDaoImpl implements ClienteDao{
    private final Logger logger = Logger.getLogger(ClienteDaoImpl.class.getName());


    @Override
    public List<Cliente> findAll() {
        final List<Cliente> all = new ArrayList<>();
        final String sql = "SELECT * FROM ER_CLIENTE";

        try (Connection conn = DatabaseConnectionFactory.create().get();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getLong("ID"),
                        rs.getString("NOME"),
                        rs.getString("CPF"),
                        rs.getString("EMAIL"),
                        rs.getString("ESTADO"),
                        rs.getString("CNH"),
                        rs.getString("SENHA"),
                        rs.getString("DATA_NASCIMENTO"), // Assumindo que Cliente usa LocalDate
                        rs.getString("PLANO")
                );
                all.add(cliente);
            }

        } catch (SQLException e) {
            this.logger.warning("Não foi possível recuperar clientes do banco de dados: " + e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return all;
    }



    @Override
    public void save(Cliente cliente, Connection connection) throws SQLException, ClienteNotSavedException {
        final String sql =
                "BEGIN INSERT INTO ER_CLIENTE (NOME, CPF, EMAIL, ESTADO, CNH, SENHA, DATA_NASCIMENTO, PLANO) VALUES (?, ?, ?, ?, ?, ?, TO_DATE(?), ?) RETURNING ID INTO ?; END;";
        CallableStatement call = connection.prepareCall(sql);

        call.setString(1, cliente.getNome());
        call.setString(2, cliente.getCpf());
        call.setString(3, cliente.getEmail());
        call.setString(4, cliente.getEstado());
        call.setString(5, cliente.getCnh());
        call.setString(6, cliente.getSenha());
        call.setDate(7, cliente.getDataNascimento());
        call.setString(8, cliente.getPlano().name());
        call.registerOutParameter(9, OracleType.NUMBER);


        int linhasAlteradas = call.executeUpdate();
        long id = call.getLong(9);

        if (linhasAlteradas == 0 || id == 0) {
            throw new ClienteNotSavedException();
        }
        cliente.setId(id);
    }

    @Override
    public Cliente update(Cliente cliente, Connection connection) throws SQLException, ClienteNotFound {
        final String sql = """
        UPDATE ER_CLIENTE
        SET NOME = ?, ESTADO = ?, CNH = ?, SENHA = ?, PLANO = ?
        WHERE ID = ?
    """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEstado());
            stmt.setString(3, cliente.getCnh());
            stmt.setString(4, cliente.getSenha());
            stmt.setString(5, cliente.getPlano().name());
            stmt.setLong(6, cliente.getId());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected != 1) {
                throw new ClienteNotFound(cliente.getId());
            }
            return cliente;
        }
    }


    @Override
    public void deleteById(Long id, Connection connection) throws ClienteNotFound, SQLException {
        final String sql = "DELETE FROM ER_CLIENTE WHERE ID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new ClienteNotFound(id);
            }
        }
    }

}
