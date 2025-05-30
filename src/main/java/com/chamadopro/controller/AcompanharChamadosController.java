package com.chamadopro.controller;

import com.chamadopro.dao.ChamadoDAO;
import com.chamadopro.model.Chamado;
import com.chamadopro.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.List;

public class AcompanharChamadosController {

    @FXML
    private ListView<String> listChamados;

    private Usuario usuarioLogado;

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
        carregarChamadosDoUsuario();
    }

    private void carregarChamadosDoUsuario() {
        ChamadoDAO dao = ChamadoDAO.getInstance();
        List<Chamado> chamados = dao.buscarPorSolicitante(usuarioLogado);

        ObservableList<String> itens = FXCollections.observableArrayList();
        for (Chamado chamado : chamados) {
            String texto = "#" + chamado.getId() + " - " + chamado.getTitulo() + " [" + chamado.getStatus() + "]";
            itens.add(texto);
        }

        listChamados.setItems(itens);
    }
}
