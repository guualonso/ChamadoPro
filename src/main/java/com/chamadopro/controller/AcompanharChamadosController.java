package com.chamadopro.controller;

import com.chamadopro.model.*;
import com.chamadopro.service.ChamadoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AcompanharChamadosController {

    @FXML
    private ListView<String> listChamados;

    @FXML
    private ComboBox<StatusChamado> statusFilter;

    @FXML
    private TextField tituloFilter;

    @FXML
    private ComboBox<Integer> comboNota;

    @FXML
    private TextArea txtFeedback;

    @FXML
    private VBox feedbackBox;

    @FXML
    private Label lblAvaliacaoCliente;

    @FXML
    private ComboBox<CategoriaProblema> categoriaFilter;

    private Usuario usuarioLogado;

    private final ChamadoService chamadoService = ChamadoService.getInstance();

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
        statusFilter.setItems(FXCollections.observableArrayList(StatusChamado.values()));
        categoriaFilter.setItems(FXCollections.observableArrayList(CategoriaProblema.values()));
        comboNota.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5));

        aplicarFiltros();

        listChamados.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> selecionarChamado());
    }

    @FXML
    public void aplicarFiltros() {
        List<Chamado> chamados = chamadoService.listarChamadosPorSolicitante(usuarioLogado);

        StatusChamado status = statusFilter.getValue();
        String tituloBusca = tituloFilter.getText().toLowerCase();
        CategoriaProblema categoriaSelecionada = categoriaFilter.getValue();

        List<Chamado> filtrados = chamados.stream()
                .filter(c -> (status == null || c.getStatus() == status))
                .filter(c -> (categoriaSelecionada == null || c.getCategoria() == categoriaSelecionada))
                .filter(c -> c.getTitulo().toLowerCase().contains(tituloBusca))
                .toList();

        ObservableList<String> itens = FXCollections.observableArrayList();
        for (Chamado chamado : filtrados) {
            String texto = "#" + chamado.getId() + " - " + chamado.getTitulo() + " [" + chamado.getStatus() + "]";
            itens.add(texto);
        }

        listChamados.setItems(itens);
    }

    private void selecionarChamado() {
        Chamado chamadoSelecionado = getChamadoSelecionado();
        if (chamadoSelecionado != null) {
            if (chamadoSelecionado.getStatus() == StatusChamado.FECHADO) {
                feedbackBox.setVisible(true);

                if (chamadoSelecionado.getAvaliacao() != null) {
                    comboNota.setDisable(true);
                    txtFeedback.setDisable(true);
                    lblAvaliacaoCliente.setText("Sua avaliação: " + chamadoSelecionado.getAvaliacao() +
                            "\nFeedback: " + chamadoSelecionado.getFeedback());
                } else {
                    comboNota.setDisable(false);
                    txtFeedback.setDisable(false);
                    lblAvaliacaoCliente.setText("");
                }
            } else {
                feedbackBox.setVisible(false);
            }
        } else {
            feedbackBox.setVisible(false);
        }
    }

    private Chamado getChamadoSelecionado() {
        String textoSelecionado = listChamados.getSelectionModel().getSelectedItem();
        if (textoSelecionado != null && textoSelecionado.contains("#")) {
            int id = Integer.parseInt(textoSelecionado.split("-")[0].replace("#", "").trim());
            return chamadoService.buscarPorId(id);
        }
        return null;
    }

    @FXML
    private void enviarAvaliacao() {
        Chamado chamadoSelecionado = getChamadoSelecionado();

        if (chamadoSelecionado != null && chamadoSelecionado.getStatus() == StatusChamado.FECHADO) {
            Integer nota = comboNota.getValue();
            String feedback = txtFeedback.getText();

            if (nota != null) {
                boolean sucesso = chamadoService.avaliarChamado(chamadoSelecionado.getId(), nota, feedback);

                if (sucesso) {
                    comboNota.setDisable(true);
                    txtFeedback.setDisable(true);
                    lblAvaliacaoCliente.setText("Sua avaliação: " + nota + "\nFeedback: " + feedback);
                    showAlert("Avaliação enviada. Obrigado!", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Erro ao salvar avaliação no banco de dados.", Alert.AlertType.ERROR);
                }
            }
        }
    }

    @FXML
    private void abrirDetalhesChamado() {
        Chamado chamado = getChamadoSelecionado();
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
        } else {
            showAlert("Selecione um chamado para ver detalhes.", Alert.AlertType.WARNING);
        }
    }

    private void showAlert(String msg, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
