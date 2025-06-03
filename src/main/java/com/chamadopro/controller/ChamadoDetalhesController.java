package com.chamadopro.controller;

import com.chamadopro.model.Chamado;
import com.chamadopro.model.Comentario;
import com.chamadopro.model.StatusChamado;
import com.chamadopro.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ChamadoDetalhesController {

    @FXML
    private Label lblTitulo;

    @FXML
    private Label lblDescricao;

    @FXML
    private TextArea txtDescricao;

    @FXML
    private Label lblStatus;

    @FXML
    private ListView<String> listComentarios;

    @FXML
    private TextArea txtNovoComentario;

    private Chamado chamado;
    private Usuario usuarioLogado;

    public void setChamado(Chamado chamado, Usuario usuarioLogado) {
        this.chamado = chamado;
        this.usuarioLogado = usuarioLogado;

        lblTitulo.setText(chamado.getTitulo());
        txtDescricao.setText(chamado.getDescricao());
        lblStatus.setText(chamado.getStatus().toString());

        switch (chamado.getStatus()) {
            case ABERTO -> lblStatus.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            case EM_ANDAMENTO -> lblStatus.setStyle("-fx-text-fill: blue; -fx-font-weight: bold;");
            case FECHADO -> lblStatus.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        }

        atualizarComentarios();
    }

    private void atualizarComentarios() {
        List<String> comentariosFormatados = chamado.getComentarios().stream()
                .map(c -> c.getAutor().getNome() + " (" + c.getDataHoraFormatada() + "): " + c.getTexto())
                .collect(Collectors.toList());

        ObservableList<String> comentarios = FXCollections.observableArrayList(comentariosFormatados);
        listComentarios.setItems(comentarios);
    }

    @FXML
    private void enviarComentario() {
        if (chamado.getId() == 0) {
            showAlert("Chamado ainda não foi salvo.", Alert.AlertType.ERROR);
            return;
        }

        String novoComentario = txtNovoComentario.getText();
        if (novoComentario == null || novoComentario.trim().isEmpty()) {
            showAlert("Digite um comentário.", Alert.AlertType.WARNING);
            return;
        }

        Comentario comentario = new Comentario(novoComentario.trim(), usuarioLogado, LocalDateTime.now());
        chamado.adicionarComentario(comentario);

        txtNovoComentario.clear();
        atualizarComentarios();

        showAlert("Comentário adicionado.", Alert.AlertType.INFORMATION);
    }

    private void showAlert(String msg, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
