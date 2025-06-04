package com.chamadopro.service;

import com.chamadopro.dao.UsuarioDAO;
import com.chamadopro.model.TipoUsuario;
import com.chamadopro.model.Usuario;

import java.util.List;
import java.util.stream.Collectors;

public class UsuarioService {

    private static final UsuarioService instance = new UsuarioService();

    private final UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();

    private UsuarioService() {}

    public static UsuarioService getInstance() {
        return instance;
    }

    public Usuario autenticar(String email, String senha) {
        return usuarioDAO.autenticar(email, senha);
    }

    public boolean cadastrar(Usuario usuario) {
        return usuarioDAO.salvar(usuario);
    }

    public List<Usuario> listarTodos() {
        return usuarioDAO.buscarTodos();
    }

    public Usuario buscarPorId(int id) {
        return usuarioDAO.buscarPorId(id);
    }

    public boolean remover(int id) {
        return usuarioDAO.remover(id);
    }

    public Usuario buscarPrimeiroTecnicoDisponivel() {
        return usuarioDAO.buscarPrimeiroTecnicoDisponivel();
    }

    public List<Usuario> buscarClientes() {
        return listarTodos().stream()
                .filter(u -> u.getTipo() == TipoUsuario.CLIENTE)
                .collect(Collectors.toList());
    }

    public boolean salvar(Usuario usuario) {
        return usuarioDAO.salvar(usuario);
    }

}
