package com.chamadopro.model;

public class Usuario {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private TipoUsuario tipo;

    public Usuario(int id, String nome, String email, String senha, TipoUsuario tipo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getSenha() { return senha; }
    public TipoUsuario getTipo() { return tipo; }

    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setEmail(String email) { this.email = email; }
    public void setSenha(String senha) { this.senha = senha; }
    public void setTipo(TipoUsuario tipo) { this.tipo = tipo; }
}
