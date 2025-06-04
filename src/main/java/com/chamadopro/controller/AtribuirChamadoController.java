package com.chamadopro.controller;

import com.chamadopro.model.Chamado;
import com.chamadopro.model.TipoUsuario;
import com.chamadopro.model.Usuario;
import com.chamadopro.service.ChamadoService;
import com.chamadopro.service.UsuarioService;
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

    private final ChamadoService chamadoService = ChamadoService.getInstance();
    private final UsuarioService usuarioService = UsuarioService.getInstance();

    @FXML
    public void initialize() {
        List<Chamado> chamadosSemResponsavel = chamadoService.buscarTodos().stream()
                .filter(c -> c.getResponsavel() == null)
                .collect(Collectors.toList());

        listChamados.setItems(FXCollections.observableArrayList(chamadosSemResponsavel));

        List<Usuario> tecnicos = usuarioService.listarTodos().stream()
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
            chamadoService.salvar(chamado);
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
