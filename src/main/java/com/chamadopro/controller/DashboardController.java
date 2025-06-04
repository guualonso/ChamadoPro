package com.chamadopro.controller;

import com.chamadopro.model.TipoUsuario;
import com.chamadopro.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardController {

    @FXML
    private Label labelBoasVindas;

    @FXML
    private VBox painelContainer;

    private Usuario usuarioLogado;

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
        labelBoasVindas.setText("Bem-vindo(a), " + usuario.getNome() + "!");
        montarPainelPorTipo(usuario.getTipo());
    }

    private void montarPainelPorTipo(TipoUsuario tipo) {
        painelContainer.getChildren().clear();

        switch (tipo) {
            case ADMIN -> {
                adicionarBotao("Gerenciar Usuários", this::abrirTelaGerenciarUsuarios);
                adicionarBotao("Atribuir Técnico", this::abrirTelaAtribuirChamado);
                adicionarBotao("Visualizar Todos os Chamados", this::abrirTelaVisualizarTodosChamados);
            }
            case TECNICO -> adicionarBotao("Visualizar Chamados", this::abrirTelaAtualizarChamado);
            case CLIENTE -> {
                adicionarBotao("Abrir Novo Chamado", this::abrirTelaChamado);
                adicionarBotao("Acompanhar Chamados", this::abrirTelaAcompanhamento);
            }
        }
    }

    private void adicionarBotao(String texto, Runnable acao) {
        Button botao = new Button(texto);
        botao.getStyleClass().add("card-button");
        botao.setOnAction(e -> acao.run());
        painelContainer.getChildren().add(botao);
    }

    private void abrirNovaJanela(String caminho, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    private void abrirNovaJanelaComUsuario(String caminho, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
            Parent root = loader.load();

            Object controller = loader.getController();

            if (controller instanceof AcompanharChamadosController c) {
                c.setUsuarioLogado(usuarioLogado);
            } else if (controller instanceof AtualizarChamadoController c) {
                c.setTecnicoLogado(usuarioLogado);
            } else if (controller instanceof AbrirChamadoController c) {
                c.setUsuarioLogado(usuarioLogado);
            }

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    private void abrirTelaChamado() {
        abrirNovaJanelaComUsuario("/fxml/abrir_chamado.fxml", "Novo Chamado");
    }

    @FXML
    private void abrirTelaAcompanhamento() {
        abrirNovaJanelaComUsuario("/fxml/acompanhar_chamados.fxml", "Acompanhar Chamados");
    }

    @FXML
    private void abrirTelaAtualizarChamado() {
        abrirNovaJanelaComUsuario("/fxml/atualizar_chamado.fxml", "Atualizar Chamado");
    }

    @FXML
    private void abrirTelaGerenciarUsuarios() {
        abrirNovaJanela("/fxml/gerenciar_usuarios.fxml", "Gerenciar Usuários");
    }

    @FXML
    private void abrirTelaAtribuirChamado() {
        abrirNovaJanela("/fxml/atribuir_chamado.fxml", "Atribuir Técnico");
    }

    @FXML
    private void abrirTelaVisualizarTodosChamados() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/acompanhar_chamados_admin.fxml"));
            Parent root = loader.load();

            AcompanharChamadosAdminController controller = loader.getController();
            controller.setUsuarioLogado(usuarioLogado);

            Stage stage = new Stage();
            stage.setTitle("Todos os Chamados");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
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
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
    }
}
