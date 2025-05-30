package com.chamadopro.dao;

import com.chamadopro.model.Usuario;
import com.chamadopro.model.TipoUsuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private final List<Usuario> usuarios = new ArrayList<>();

    public UsuarioDAO() {
        usuarios.add(new Usuario(1, "Admin", "admin@chp.com", "admin123", TipoUsuario.ADMIN));
        usuarios.add(new Usuario(2, "Técnico", "tecnico@chp.com", "tecnico123", TipoUsuario.TECNICO));
        usuarios.add(new Usuario(3, "Cliente", "cliente@chp.com", "cliente123", TipoUsuario.CLIENTE));
    }

    public Usuario autenticar(String email, String senha) {
        return usuarios.stream()
                .filter(u -> u.getEmail().equals(email) && u.getSenha().equals(senha))
                .findFirst()
                .orElse(null);
    }

    public boolean salvar(Usuario usuario) {
        usuario.setId(usuarios.size() + 1);
        usuarios.add(usuario);
        return true;
    }

    public List<Usuario> buscarTodos() {
        return usuarios;
    }

    public boolean remover(int id) {
        return usuarios.removeIf(u -> u.getId() == id);
    }

}