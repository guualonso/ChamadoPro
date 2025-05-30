package com.chamadopro.util;

public class Validator {

    public static boolean campoVazio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

    public static boolean emailValido(String email) {
        return email != null && email.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$");
    }

    public static boolean senhaForte(String senha) {
        return senha != null && senha.length() >= 6;
    }
}
