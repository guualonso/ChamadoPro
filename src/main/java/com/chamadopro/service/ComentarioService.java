package com.chamadopro.service;

import com.chamadopro.dao.ComentarioDAO;
import com.chamadopro.model.Comentario;

import java.util.List;

public class ComentarioService {

    private static final ComentarioService instance = new ComentarioService();
    private final ComentarioDAO comentarioDAO = ComentarioDAO.getInstance();

    private ComentarioService() {}

    public static ComentarioService getInstance() {
        return instance;
    }

    public boolean adicionarComentario(int chamadoId, Comentario comentario) {
        return comentarioDAO.salvarComentario(chamadoId, comentario);
    }

    public List<Comentario> listarPorChamado(int chamadoId) {
        return comentarioDAO.buscarPorChamado(chamadoId);
    }
}
