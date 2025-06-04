package com.chamadopro.service;

import com.chamadopro.dao.ChamadoDAO;
import com.chamadopro.model.*;

import java.util.List;

public class ChamadoService {

    private static ChamadoService instance = new ChamadoService();
    private final ChamadoDAO chamadoDAO = ChamadoDAO.getInstance();

    private ChamadoService() {}

    public static ChamadoService getInstance() {
        if (instance == null) {
            instance = new ChamadoService();
        }
        return instance;
    }

    public boolean abrirChamado(Chamado chamado) {
        chamado.setStatus(StatusChamado.ABERTO);
        chamado.setDataHoraAbertura(java.time.LocalDateTime.now());
        return chamadoDAO.salvar(chamado);
    }

    public List<Chamado> listarChamadosPorSolicitante(Usuario usuario) {
        return chamadoDAO.buscarPorSolicitante(usuario);
    }

    public List<Chamado> listarChamadosPorResponsavel(Usuario tecnico) {
        return chamadoDAO.buscarPorResponsavel(tecnico);
    }

    public List<Chamado> listarTodosChamados() {
        return chamadoDAO.buscarTodos();
    }

    public void atualizarStatusChamado(int idChamado, StatusChamado novoStatus) {
        chamadoDAO.atualizarStatus(idChamado, novoStatus);
    }

    public boolean avaliarChamado(int idChamado, int avaliacao, String feedback) {
        return chamadoDAO.salvarAvaliacao(idChamado, avaliacao, feedback);
    }

    public Chamado buscarPorId(int id) {
        return chamadoDAO.buscarPorId(id);
    }

    public boolean salvar(Chamado chamado) {
        return chamadoDAO.salvar(chamado);
    }

    public List<Chamado> buscarTodos() {
        return chamadoDAO.buscarTodos();
    }
}
