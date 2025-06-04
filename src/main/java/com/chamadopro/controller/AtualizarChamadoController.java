package com.chamadopro.controller;

import com.chamadopro.model.Chamado;
import com.chamadopro.model.Comentario;
import com.chamadopro.model.StatusChamado;
import com.chamadopro.model.Usuario;
import com.chamadopro.service.ChamadoService;
import com.chamadopro.service.ComentarioService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class AtualizarChamadoController {

    @FXML
    private ListView<Chamado> listChamados;

    @FXML
    private ComboBox<StatusChamado> comboStatus;

    @FXML
    private Button btnAtualizar;

    @FXML
    private TextArea txtComentario;

    private Usuario tecnicoLogado;

    private final ChamadoService chamadoService = ChamadoService.getInstance();

    private final ComentarioService comentarioService = ComentarioService.getInstance();

    public void setTecnicoLogado(Usuario tecnico) {
        this.tecnicoLogado = tecnico;
        carregarChamados();
    }

    private void carregarChamados() {
        List<Chamado> chamados = chamadoService.listarChamadosPorResponsavel(tecnicoLogado);
        ObservableList<Chamado> obsList = FXCollections.observableArrayList(chamados);
        listChamados.setItems(obsList);
    }

    @FXML
    private void initialize() {
        comboStatus.setItems(FXCollections.observableArrayList(StatusChamado.values()));
        btnAtualizar.setDisable(true);

        listChamados.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> btnAtualizar.setDisable(newSel == null));
    }

    @FXML
    private void atualizarStatusChamado() {
        Chamado chamadoSelecionado = listChamados.getSelectionModel().getSelectedItem();
        StatusChamado novoStatus = comboStatus.getValue();

        if (chamadoSelecionado != null && novoStatus != null) {
            chamadoSelecionado.setStatus(novoStatus);
            chamadoService.atualizarStatusChamado(chamadoSelecionado.getId(), novoStatus);
            showAlert("Status atualizado com sucesso!", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Selecione um chamado e um novo status.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void visualizarChamado() {
        Chamado chamadoSelecionado = listChamados.getSelectionModel().getSelectedItem();
        if (chamadoSelecionado != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Detalhes do Chamado");
            alert.setHeaderText("Chamado nº " + chamadoSelecionado.getId());
            alert.setContentText(
                    "Título: " + chamadoSelecionado.getTitulo() + "\n" +
                            "Descrição: " + chamadoSelecionado.getDescricao() + "\n" +
                            "Status: " + chamadoSelecionado.getStatus() + "\n" +
                            "Cliente: " + chamadoSelecionado.getSolicitante().getNome()
            );
            alert.showAndWait();
        } else {
            showAlert("Selecione um chamado para visualizar.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void adicionarComentario() {
        Chamado chamadoSelecionado = listChamados.getSelectionModel().getSelectedItem();
        String textoComentario = txtComentario.getText();

        if (chamadoSelecionado != null && textoComentario != null && !textoComentario.isEmpty()) {
            Comentario comentario = new Comentario(textoComentario, tecnicoLogado, LocalDateTime.now());

            boolean sucesso = comentarioService.adicionarComentario(chamadoSelecionado.getId(), comentario);

            if (sucesso) {
                showAlert("Comentário adicionado com sucesso!", Alert.AlertType.INFORMATION);
                txtComentario.clear();
            } else {
                showAlert("Erro ao salvar o comentário.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Selecione um chamado e insira um comentário.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void abrirDetalhesChamado() {
        Chamado chamadoSelecionado = listChamados.getSelectionModel().getSelectedItem();
        if (chamadoSelecionado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/chamado_detalhes.fxml"));
                Parent root = loader.load();

                ChamadoDetalhesController controller = loader.getController();
                controller.setChamado(chamadoSelecionado, tecnicoLogado);

                Stage stage = new Stage();
                stage.setTitle("Detalhes do Chamado");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Selecione um chamado para ver detalhes.", Alert.AlertType.WARNING);
        }
    }

    private void showAlert(String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Atualizar Chamado");
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
