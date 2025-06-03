package com.chamadopro.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Comentario {
    private String texto;
    private Usuario autor;
    private LocalDateTime dataHora;

    public Comentario(String texto, Usuario autor, LocalDateTime dataHora) {
        this.texto = texto;
        this.autor = autor;
        this.dataHora = dataHora;
    }

    public String getTexto() { return texto; }
    public Usuario getAutor() { return autor; }
    public LocalDateTime getDataHora() { return dataHora; }

    public void setTexto(String texto) { this.texto = texto; }
    public void setAutor(Usuario autor) { this.autor = autor; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public String getDataHoraFormatada() {
        return dataHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}
