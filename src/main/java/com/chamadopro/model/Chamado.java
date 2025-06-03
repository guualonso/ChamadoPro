package com.chamadopro.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import com.chamadopro.dao.ComentarioDAO;

public class Chamado {
    private int id;
    private String titulo;
    private String descricao;
    private StatusChamado status;
    private Usuario solicitante;
    private Usuario responsavel;
    private LocalDateTime dataHoraAbertura;
    private Integer avaliacao;
    private String feedback;
    private CategoriaProblema categoria;

    public Chamado() {}

    public Chamado(int id, String titulo, String descricao, StatusChamado status,
                   Usuario solicitante, Usuario responsavel, LocalDateTime dataHoraAbertura,
                   CategoriaProblema categoria) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = status;
        this.solicitante = solicitante;
        this.responsavel = responsavel;
        this.dataHoraAbertura = dataHoraAbertura;
        this.categoria = categoria;
    }

    // Getters
    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescricao() { return descricao; }
    public StatusChamado getStatus() { return status; }
    public Usuario getSolicitante() { return solicitante; }
    public Usuario getResponsavel() { return responsavel; }
    public LocalDateTime getDataHoraAbertura() { return dataHoraAbertura; }
    public Integer getAvaliacao() { return avaliacao; }
    public String getFeedback() { return feedback; }
    public CategoriaProblema getCategoria() { return categoria; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setStatus(StatusChamado status) { this.status = status; }
    public void setSolicitante(Usuario solicitante) { this.solicitante = solicitante; }
    public void setResponsavel(Usuario responsavel) { this.responsavel = responsavel; }
    public void setDataHoraAbertura(LocalDateTime dataHoraAbertura) { this.dataHoraAbertura = dataHoraAbertura; }
    public void setAvaliacao(Integer avaliacao) { this.avaliacao = avaliacao; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
    public void setCategoria(CategoriaProblema categoria) { this.categoria = categoria; }

    // Comentários
    public void adicionarComentario(Comentario comentario) {
        ComentarioDAO.getInstance().salvarComentario(this.id, comentario);
    }

    public List<Comentario> getComentarios() {
        return ComentarioDAO.getInstance().buscarPorChamado(this.id);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String dataFormatada = dataHoraAbertura.format(formatter);
        return "Chamado #" + id + " - " + titulo + " (" + dataFormatada + ")";
    }
}
