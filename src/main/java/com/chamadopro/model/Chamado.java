package com.chamadopro.model;

public class Chamado {
    private int id;
    private String titulo;
    private String descricao;
    private StatusChamado status;
    private Usuario solicitante;
    private Usuario responsavel;

    public Chamado(int id, String titulo, String descricao, StatusChamado status, Usuario solicitante, Usuario responsavel) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = status;
        this.solicitante = solicitante;
        this.responsavel = responsavel;
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescricao() { return descricao; }
    public StatusChamado getStatus() { return status; }
    public Usuario getSolicitante() { return solicitante; }
    public Usuario getResponsavel() { return responsavel; }

    public void setId(int id) { this.id = id; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setStatus(StatusChamado status) { this.status = status; }
    public void setSolicitante(Usuario solicitante) { this.solicitante = solicitante; }
    public void setResponsavel(Usuario responsavel) { this.responsavel = responsavel; }
}
