package com.chamadopro.controller;

import com.chamadopro.model.*;
import com.chamadopro.service.ChamadoService;
import com.chamadopro.service.UsuarioService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class AcompanharChamadosAdminController {

    @FXML
    private ComboBox<Usuario> comboUsuario;

    @FXML
    private ComboBox<StatusChamado> comboStatus;

    @FXML
    private TextField campoTitulo;

    @FXML
    private ListView<String> listChamados;

    private Usuario usuarioLogado;

    private final UsuarioService usuarioService = UsuarioService.getInstance();
    private final ChamadoService chamadoService = ChamadoService.getInstance();

    @FXML
    public void initialize() {
        List<Usuario> clientes = usuarioService.buscarClientes();

        comboUsuario.setItems(FXCollections.observableArrayList(clientes));
        comboStatus.setItems(FXCollections.observableArrayList(StatusChamado.values()));

        aplicarFiltros();
    }

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
    }

    @FXML
    public void aplicarFiltros() {
        List<Chamado> todos = chamadoService.buscarTodos();

        Usuario clienteSelecionado = comboUsuario.getValue();
        StatusChamado statusSelecionado = comboStatus.getValue();
        String buscaTitulo = campoTitulo.getText() != null ? campoTitulo.getText().toLowerCase() : "";

        List<Chamado> filtrados = todos.stream()
                .filter(c -> clienteSelecionado == null || c.getSolicitante().equals(clienteSelecionado))
                .filter(c -> statusSelecionado == null || c.getStatus() == statusSelecionado)
                .filter(c -> c.getTitulo().toLowerCase().contains(buscaTitulo))
                .toList();

        List<String> linhas = filtrados.stream()
                .map(c -> "#" + c.getId() + " - " + c.getTitulo() + " | " +
                        c.getSolicitante().getNome() + " [" + c.getStatus() + "]")
                .collect(Collectors.toList());

        listChamados.setItems(FXCollections.observableArrayList(linhas));
    }

    @FXML
    private void abrirDetalhesChamado() {
        String textoSelecionado = listChamados.getSelectionModel().getSelectedItem();
        if (textoSelecionado == null || !textoSelecionado.startsWith("#")) {
            return;
        }

        int id = Integer.parseInt(textoSelecionado.split("-")[0].replace("#", "").trim());
        Chamado chamado = chamadoService.buscarPorId(id);

        if (chamado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/chamado_detalhes.fxml"));
                Parent root = loader.load();

                ChamadoDetalhesController controller = loader.getController();
                controller.setChamado(chamado, usuarioLogado);

                Stage stage = new Stage();
                stage.setTitle("Detalhes do Chamado");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }
}
