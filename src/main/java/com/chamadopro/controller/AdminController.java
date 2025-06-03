package com.chamadopro.controller;

import com.chamadopro.dao.ChamadoDAO;
import com.chamadopro.dao.UsuarioDAO;
import com.chamadopro.model.Chamado;
import com.chamadopro.model.TipoUsuario;
import com.chamadopro.model.Usuario;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;
import java.util.Objects;

public class AdminController {

    @FXML
    private ListView<String> listaUsuarios;

    @FXML
    private TextField nomeField, emailField, senhaField;

    @FXML
    private ComboBox<TipoUsuario> tipoUsuarioCombo;

    @FXML
    private Label lblMediaAvaliacao;

    private final UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();


    @FXML
    private void initialize() {
        tipoUsuarioCombo.setItems(FXCollections.observableArrayList(TipoUsuario.values()));
        atualizarLista();
        atualizarMedia();
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

    private double calcularMediaAvaliacao() {
        List<Chamado> chamados = ChamadoDAO.getInstance().buscarTodos();
        List<Integer> notas = chamados.stream()
                .map(Chamado::getAvaliacao)
                .filter(Objects::nonNull)
                .toList();

        if (notas.isEmpty()) return 0.0;
        double soma = notas.stream().mapToInt(Integer::intValue).sum();
        return soma / (double) notas.size();
    }

    private void atualizarMedia() {
        double media = calcularMediaAvaliacao();
        lblMediaAvaliacao.setText(String.format("Média de avaliações: %.2f", media));
    }

    private void showAlert(String msg, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
