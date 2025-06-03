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
import java.util.stream.Collectors;

public class AtribuirChamadoController {

    @FXML
    private ListView<Chamado> listChamados;

    @FXML
    private ComboBox<Usuario> comboTecnicos;

    private final ChamadoDAO chamadoDAO = ChamadoDAO.getInstance();
    private final UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();

    @FXML
    public void initialize() {
        List<Chamado> chamadosSemResponsavel = chamadoDAO.buscarTodos().stream()
                .filter(c -> c.getResponsavel() == null)
                .collect(Collectors.toList());

        listChamados.setItems(FXCollections.observableArrayList(chamadosSemResponsavel));

        List<Usuario> tecnicos = usuarioDAO.buscarTodos().stream()
                .filter(u -> u.getTipo() == TipoUsuario.TECNICO)
                .collect(Collectors.toList());

        comboTecnicos.setItems(FXCollections.observableArrayList(tecnicos));
    }

    @FXML
    public void atribuirTecnico() {
        Chamado chamado = listChamados.getSelectionModel().getSelectedItem();
        Usuario tecnico = comboTecnicos.getValue();

        if (chamado != null && tecnico != null) {
            chamado.setResponsavel(tecnico);
            listChamados.getItems().remove(chamado);
            showAlert("Técnico atribuído com sucesso!", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Selecione um chamado e um técnico.", Alert.AlertType.WARNING);
        }
    }

    private void showAlert(String msg, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
