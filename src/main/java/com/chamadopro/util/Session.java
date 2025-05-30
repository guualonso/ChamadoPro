package com.chamadopro.util;

import com.chamadopro.model.Usuario;

public class Session {

    private static Usuario usuarioLogado;

    public static void setUsuario(Usuario usuario) {
        usuarioLogado = usuario;
    }

    public static Usuario getUsuario() {
        return usuarioLogado;
    }

    public static void encerrar() {
        usuarioLogado = null;
    }
}
