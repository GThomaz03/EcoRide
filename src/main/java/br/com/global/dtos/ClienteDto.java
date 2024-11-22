package br.com.global.dtos;

import br.com.global.models.Plano;

import java.sql.Date;
import java.sql.SQLData;

public class ClienteDto {
    private String nome;
    private String cpf;
    private String email;
    private String estado;
    private String cnh;
    private String senha;
    private String dataNascimento;
    private String plano;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCnh() {
        return cnh;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String  getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String  dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String  getPlano() {
        return plano;
    }

    public void setPlano(String plano) {
        this.plano = plano;
    }
}
