package com.chamadopro.controller;

import com.chamadopro.dao.UsuarioDAO;
import com.chamadopro.model.Usuario;

import java.util.List;

public class UsuarioController {

    private final UsuarioDAO usuarioDAO;

    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public List<Usuario> listarUsuarios() {
        return usuarioDAO.buscarTodos();
    }

    public boolean cadastrarUsuario(Usuario usuario) {
        return usuarioDAO.salvar(usuario);
    }

    public boolean removerUsuario(int id) {
        return usuarioDAO.remover(id);
    }
}
