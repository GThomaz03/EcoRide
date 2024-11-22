package br.com.global.models;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Cliente {
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String estado;
    private String cnh;
    private String senha;
    private Date dataNascimento;
    private Plano plano;

    public Cliente(Long id, String nome, String cpf, String email, String estado, String cnh, String senha, String dataNascimento, String  plano) throws ParseException {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.estado = estado;
        this.cnh = cnh;
        this.senha = senha;
        this.dataNascimento = convertStringToSqlDate(dataNascimento);
        this.plano = convertStringToPlano(plano);
    }

    public Cliente(String nome, String cpf, String email, String estado, String cnh, String senha, String dataNascimento, String  plano) throws ParseException {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.estado = estado;
        this.cnh = cnh;
        this.senha = senha;
        this.dataNascimento = convertStringToSqlDate(dataNascimento);
        this.plano = convertStringToPlano(plano);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public static Date convertStringToSqlDate(String dateString) throws ParseException {
        // Formato da data esperado
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // Converter para java.util.Date
        java.util.Date utilDate = dateFormat.parse(dateString);
        // Converter para java.sql.Date
        return new Date(utilDate.getTime());
    }

    public static Plano convertStringToPlano(String planoString) throws ParseException {
        return Plano.valueOf(planoString);
    }

}
