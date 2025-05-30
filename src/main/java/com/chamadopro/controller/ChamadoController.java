package com.chamadopro.controller;

import com.chamadopro.dao.ChamadoDAO;
import com.chamadopro.model.Chamado;
import com.chamadopro.model.StatusChamado;
import com.chamadopro.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ChamadoController {

    @FXML
    private TextField tituloField;

    @FXML
    private TextArea descricaoField;

    private Usuario usuarioLogado;

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
    }

    @FXML
    protected void handleAbrirChamado() {
        String titulo = tituloField.getText();
        String descricao = descricaoField.getText();

        if (titulo.isEmpty() || descricao.isEmpty()) {
            showAlert("Preencha todos os campos.", Alert.AlertType.WARNING);
            return;
        }

        Chamado novoChamado = new Chamado(
                0,
                titulo,
                descricao,
                StatusChamado.ABERTO,
                usuarioLogado,
                null
        );

        ChamadoDAO dao = ChamadoDAO.getInstance();
        dao.salvar(novoChamado);

        showAlert("Chamado aberto com sucesso!", Alert.AlertType.INFORMATION);

        // Limpar os campos
        tituloField.clear();
        descricaoField.clear();
    }

    private void showAlert(String msg, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Abertura de Chamado");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
