package com.chamadopro.controller;

import com.chamadopro.model.Chamado;
import com.chamadopro.model.Comentario;
import com.chamadopro.model.Usuario;
import com.chamadopro.service.ComentarioService;
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

    private final ComentarioService comentarioService = ComentarioService.getInstance();

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
        List<Comentario> comentariosBanco = comentarioService.listarPorChamado(chamado.getId());

        List<String> comentariosFormatados = comentariosBanco.stream()
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
        boolean sucesso = comentarioService.adicionarComentario(chamado.getId(), comentario);

        if (sucesso) {
            txtNovoComentario.clear();
            atualizarComentarios(); // recarrega do banco
            showAlert("Comentário adicionado.", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Erro ao salvar o comentário no banco de dados.", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String msg, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
