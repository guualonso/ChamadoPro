package com.chamadopro.dao;

import com.chamadopro.model.Chamado;
import com.chamadopro.model.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChamadoDAO {

    private static final ChamadoDAO instance = new ChamadoDAO();
    private final List<Chamado> chamados = new ArrayList<>();
    private int idCounter = 1;

    private ChamadoDAO() {}

    public static ChamadoDAO getInstance() {
        return instance;
    }

    public boolean salvar(Chamado chamado) {
        chamado.setId(idCounter++);
        chamados.add(chamado);
        return true;
    }

    public List<Chamado> buscarPorSolicitante(Usuario usuario) {
        return chamados.stream()
                .filter(c -> c.getSolicitante().getEmail().equals(usuario.getEmail()))
                .collect(Collectors.toList());
    }

    public List<Chamado> buscarPorResponsavel(Usuario tecnico) {
        return chamados.stream()
                .filter(c -> c.getResponsavel() != null &&
                        c.getResponsavel().getEmail().equals(tecnico.getEmail()))
                .collect(Collectors.toList());
    }

    public List<Chamado> buscarTodos() {
        return chamados;
    }
}
