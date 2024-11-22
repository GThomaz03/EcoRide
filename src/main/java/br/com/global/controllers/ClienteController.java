package br.com.global.controllers;

import br.com.global.dtos.ClienteDto;
import br.com.global.exceptions.ClienteInvalid;
import br.com.global.exceptions.ClienteNotFound;
import br.com.global.exceptions.ClienteNotSavedException;
import br.com.global.exceptions.ClienteNotUpdatedException;
import br.com.global.models.Cliente;
import br.com.global.services.ClienteService;
import br.com.global.services.ClienteServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Map;



@Path("/ecoride")
public class ClienteController {

    private ClienteService clienteService = ClienteServiceFactory.create();

    @GET
    @Path("/localizar-todos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response localizarTodos() {

        return Response.status(Response.Status.OK)
                .entity(this.clienteService.listarTodos())
                .build();
    }

    @POST
    @Path("/cadastrar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrar(ClienteDto dto) {
        try{
            Cliente cliente = new Cliente(dto.getNome(), dto.getCpf(), dto.getEmail(), dto.getEstado(), dto.getCnh(), dto.getSenha(), dto.getDataNascimento(), dto.getPlano());
            cliente = this.clienteService.create(cliente);
            return Response.status(Response.Status.CREATED)
                    .entity(cliente).build();
        }catch (ClienteInvalid e){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("mensagem", "Dados inválidos"))
                    .build();
        }catch (SQLException | ClienteNotSavedException s){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("mensagem", "não foi possível salvar o registro"))
                    .build();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePessoa(@PathParam("id") Long id,ClienteDto dto){
        try {
            Cliente cliente = this.clienteService
                    .update(new Cliente(id, dto.getNome(), dto.getCpf(), dto.getEmail(), dto.getEstado(), dto.getCnh(), dto.getSenha(), dto.getDataNascimento(), dto.getPlano()));
            return Response.status(Response.Status.OK)
                    .entity(cliente).build();
        } catch (ClienteNotFound e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("mensagem","id não existe")).build();
        } catch (ClienteNotUpdatedException u){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("messagem","não foi possível atualizar o registro"))
                    .build();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (ClienteInvalid e) {
            throw new RuntimeException(e);
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletePessoa(@PathParam("id") Long id){
        try{
            this.clienteService.delete(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (ClienteNotFound e){
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (SQLException s){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("messagem","não foi possível deletar o registro"))
                    .build();
        }

    }

}
