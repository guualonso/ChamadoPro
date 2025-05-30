package com.chamadopro.controller;

import com.chamadopro.dao.UsuarioDAO;
import com.chamadopro.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField senhaField;

    @FXML
    protected void handleLoginButtonAction() {
        String email = emailField.getText();
        String senha = senhaField.getText();

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.autenticar(email, senha);

        if (usuario != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
                Parent root = loader.load();

                DashboardController controller = loader.getController();
                controller.setUsuarioLogado(usuario);

                Stage stage = (Stage) emailField.getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (IOException e) {
                showAlert("Erro ao carregar o painel: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Credenciais inválidas.", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Login");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
