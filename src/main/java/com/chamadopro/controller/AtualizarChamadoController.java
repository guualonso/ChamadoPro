package com.chamadopro.controller;

import com.chamadopro.dao.ChamadoDAO;
import com.chamadopro.model.Chamado;
import com.chamadopro.model.StatusChamado;
import com.chamadopro.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class AtualizarChamadoController {

    @FXML
    private ListView<Chamado> listChamados;

    @FXML
    private ComboBox<StatusChamado> comboStatus;

    @FXML
    private Button btnAtualizar;

    private Usuario tecnicoLogado;

    private ChamadoDAO chamadoDAO;

    public void setTecnicoLogado(Usuario tecnico) {
        this.tecnicoLogado = tecnico;
        this.chamadoDAO = ChamadoDAO.getInstance();
        carregarChamados();
    }

    private void carregarChamados() {
        List<Chamado> chamados = chamadoDAO.buscarPorResponsavel(tecnicoLogado);
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
            // Aqui seria o update no banco (simulação)
            showAlert("Status atualizado com sucesso!", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Selecione um chamado e um novo status.", Alert.AlertType.WARNING);
        }
    }

    private void showAlert(String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Atualizar Chamado");
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
