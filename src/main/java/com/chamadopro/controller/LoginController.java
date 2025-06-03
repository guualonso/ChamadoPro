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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField senhaField;

    @FXML
    private ImageView logoImage;

    @FXML
    protected void handleLoginButtonAction() {
        String email = emailField.getText();
        String senha = senhaField.getText();

        UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
        Usuario usuario = usuarioDAO.autenticar(email, senha);

        if (usuario != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
                Parent root = loader.load();
                DashboardController controller = loader.getController();
                controller.setUsuarioLogado(usuario);
                Stage stage = (Stage) emailField.getScene().getWindow();
                Scene dashboardScene = new Scene(root, 1366, 768);
                dashboardScene.getStylesheets().add(getClass().getResource("/css/estilo.css").toExternalForm());

                stage.setScene(dashboardScene);

                stage.setWidth(1366);
                stage.setHeight(768);
                stage.setMinWidth(1024);
                stage.setMinHeight(600);
                stage.setResizable(true);

            } catch (IOException e) {
                showAlert("Erro ao carregar o painel: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Credenciais inválidas.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void initialize() {
        logoImage.setImage(new Image(getClass().getResourceAsStream("/images/logo.png")));
    }


    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Login");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
