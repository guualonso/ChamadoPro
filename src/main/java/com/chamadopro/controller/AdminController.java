package com.chamadopro.controller;

import com.chamadopro.dao.UsuarioDAO;
import com.chamadopro.model.TipoUsuario;
import com.chamadopro.model.Usuario;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class AdminController {

    @FXML
    private ListView<String> listaUsuarios;

    @FXML
    private TextField nomeField, emailField, senhaField;

    @FXML
    private ComboBox<TipoUsuario> tipoUsuarioCombo;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML
    private void initialize() {
        tipoUsuarioCombo.setItems(FXCollections.observableArrayList(TipoUsuario.values()));
        atualizarLista();
    }

    private void atualizarLista() {
        List<Usuario> usuarios = usuarioDAO.buscarTodos();
        listaUsuarios.getItems().clear();
        for (Usuario u : usuarios) {
            listaUsuarios.getItems().add(u.getNome() + " - " + u.getTipo());
        }
    }

    @FXML
    private void cadastrarUsuario() {
        String nome = nomeField.getText();
        String email = emailField.getText();
        String senha = senhaField.getText();
        TipoUsuario tipo = tipoUsuarioCombo.getValue();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || tipo == null) {
            showAlert("Preencha todos os campos!", Alert.AlertType.WARNING);
            return;
        }

        Usuario novo = new Usuario(0, nome, email, senha, tipo);
        usuarioDAO.salvar(novo);
        atualizarLista();
        limparCampos();
        showAlert("Usuário cadastrado!", Alert.AlertType.INFORMATION);
    }

    private void limparCampos() {
        nomeField.clear();
        emailField.clear();
        senhaField.clear();
        tipoUsuarioCombo.getSelectionModel().clearSelection();
    }

    private void showAlert(String msg, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
