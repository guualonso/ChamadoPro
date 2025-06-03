package com.chamadopro.controller;

import com.chamadopro.dao.ChamadoDAO;
import com.chamadopro.dao.UsuarioDAO;
import com.chamadopro.model.CategoriaProblema;
import com.chamadopro.model.Chamado;
import com.chamadopro.model.StatusChamado;
import com.chamadopro.model.Usuario;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDateTime;

public class AbrirChamadoController {

    @FXML
    private TextField tituloField;

    @FXML
    private TextArea descricaoField;

    @FXML
    private ComboBox<CategoriaProblema> comboCategoria;

    private Usuario usuarioLogado;

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
    }

    @FXML
    public void initialize() {
        comboCategoria.setItems(FXCollections.observableArrayList(CategoriaProblema.values()));
    }

    @FXML
    protected void handleAbrirChamado() {
        String titulo = tituloField.getText();
        String descricao = descricaoField.getText();
        CategoriaProblema categoria = comboCategoria.getValue();

        if (usuarioLogado == null) {
            showAlert("Usuário não autenticado.", Alert.AlertType.ERROR);
            return;
        }

        if (titulo.isEmpty() || descricao.isEmpty() || categoria == null) {
            showAlert("Preencha todos os campos e selecione uma categoria.", Alert.AlertType.WARNING);
            return;
        }

        Usuario tecnico = UsuarioDAO.getInstance().buscarPrimeiroTecnicoDisponivel();

        if (tecnico == null) {
            showAlert("Nenhum técnico disponível no momento.", Alert.AlertType.WARNING);
            return;
        }

        Chamado novoChamado = new Chamado(
                0,
                titulo,
                descricao,
                StatusChamado.ABERTO,
                usuarioLogado,
                tecnico,
                LocalDateTime.now(),
                categoria
        );

        boolean sucesso = ChamadoDAO.getInstance().salvar(novoChamado);

        if (sucesso) {
            showAlert("Chamado aberto e técnico atribuído com sucesso!", Alert.AlertType.INFORMATION);
            tituloField.clear();
            descricaoField.clear();
            comboCategoria.getSelectionModel().clearSelection();
            tituloField.getScene().getWindow().hide();
        } else {
            showAlert("Erro ao abrir chamado.", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String msg, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Abertura de Chamado");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
