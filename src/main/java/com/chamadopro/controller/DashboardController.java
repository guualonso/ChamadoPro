package com.chamadopro.controller;

import com.chamadopro.model.TipoUsuario;
import com.chamadopro.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

    @FXML
    private Label labelBoasVindas;

    @FXML
    private VBox painelAdmin;

    @FXML
    private VBox painelTecnico;

    @FXML
    private VBox painelCliente;

    private Usuario usuarioLogado;

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
        labelBoasVindas.setText("Bem-vindo(a), " + usuario.getNome() + "!");
        mostrarPainelPorTipo(usuario.getTipo());
    }

    private void mostrarPainelPorTipo(TipoUsuario tipo) {
        painelAdmin.setVisible(tipo == TipoUsuario.ADMIN);
        painelTecnico.setVisible(tipo == TipoUsuario.TECNICO);
        painelCliente.setVisible(tipo == TipoUsuario.CLIENTE);
    }


    @FXML
    private void abrirTelaChamado() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/abrir_chamado.fxml"));
            Parent root = loader.load();

            ChamadoController controller = loader.getController();
            controller.setUsuarioLogado(usuarioLogado); // agora vai funcionar

            Stage stage = new Stage();
            stage.setTitle("Novo Chamado");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirTelaAcompanhamento() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/acompanhar_chamados.fxml"));
            Parent root = loader.load();

            AcompanharChamadosController controller = loader.getController();
            controller.setUsuarioLogado(usuarioLogado);

            Stage stage = new Stage();
            stage.setTitle("Acompanhamento de Chamados");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirTelaAtualizarChamado() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/atualizar_chamado.fxml"));
            Parent root = loader.load();

            AtualizarChamadoController controller = loader.getController();
            controller.setTecnicoLogado(usuarioLogado);

            Stage stage = new Stage();
            stage.setTitle("Atualizar Chamado");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirTelaGerenciarUsuarios() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/gerenciar_usuarios.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Gerenciar Usuários");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void sair() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) labelBoasVindas.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("ChamadoPro - Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
