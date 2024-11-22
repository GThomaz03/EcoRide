package br.com.global;

import br.com.global.config.DatabaseConnection;
import br.com.global.config.DatabaseConnectionFactory;
import br.com.global.daos.ClienteDao;
import br.com.global.daos.ClienteDaoFactory;
import br.com.global.exceptions.ClienteInvalid;
import br.com.global.exceptions.ClienteNotFound;
import br.com.global.exceptions.ClienteNotSavedException;
import br.com.global.exceptions.ClienteNotUpdatedException;
import br.com.global.models.Cliente;
import br.com.global.models.Plano;
import br.com.global.services.ClienteService;
import br.com.global.services.ClienteServiceFactory;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

public class Abacaxi {
    public static void main(String[] args) throws ParseException, SQLException, ClienteNotSavedException {
        ClienteService clienteService = ClienteServiceFactory.create();

        Cliente cliente1 = new Cliente(
                19L,
                "GaBRIEL",
                "12345678981",
                "joao.silva2@example.com",
                "SP",
                "CNH12845",
                "senha123",
                "1990-05-15",
                "BASICO"
        );

        try {
            clienteService.update(cliente1);
        } catch (ClienteNotFound e) {
            throw new RuntimeException(e);
        } catch (ClienteNotUpdatedException e) {
            throw new RuntimeException(e);
        } catch (ClienteInvalid e) {
            throw new RuntimeException(e);
        }


        // Inserindo novos clientes
        try {
            clienteService.create(cliente1);

            System.out.println("Clientes inseridos com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao inserir clientes: " + e.getMessage());
        }

        // Listando todos os clientes
        try {
            List<Cliente> clientes = clienteService.listarTodos();
            System.out.println("\nLista de clientes cadastrados:");
            for (Cliente cliente : clientes) {
                System.out.printf("ID: %d, Nome: %s, CPF: %s, Email: %s, Estado: %s, CNH: %s, Nascimento: %s%n",
                        cliente.getId(),
                        cliente.getNome(),
                        cliente.getCpf(),
                        cliente.getEmail(),
                        cliente.getEstado(),
                        cliente.getCnh(),
                        cliente.getDataNascimento()
                );
            }
        } catch (Exception e) {
            System.err.println("Erro ao listar clientes: " + e.getMessage());
        }





    }
}
