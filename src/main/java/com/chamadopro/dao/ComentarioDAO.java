package com.chamadopro.dao;

import com.chamadopro.config.DatabaseConfig;
import com.chamadopro.model.Comentario;
import com.chamadopro.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComentarioDAO {

    private static final ComentarioDAO instance = new ComentarioDAO();

    private ComentarioDAO() {}

    public static ComentarioDAO getInstance() {
        return instance;
    }

    public boolean salvarComentario(int chamadoId, Comentario comentario) {
        String sql = "INSERT INTO comentarios (texto, data_hora, autor_id, chamado_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, comentario.getTexto());
            stmt.setTimestamp(2, Timestamp.valueOf(comentario.getDataHora()));
            stmt.setInt(3, comentario.getAutor().getId());
            stmt.setInt(4, chamadoId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Comentario> buscarPorChamado(int chamadoId) {
        List<Comentario> comentarios = new ArrayList<>();
        String sql = """
                SELECT c.texto, c.data_hora, c.autor_id,
                       u.nome, u.email, u.tipo
                FROM comentarios c
                JOIN usuarios u ON c.autor_id = u.id
                WHERE c.chamado_id = ?
                ORDER BY c.data_hora ASC
                """;

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, chamadoId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Usuario autor = new Usuario(
                        rs.getInt("autor_id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        "",
                        com.chamadopro.model.TipoUsuario.valueOf(rs.getString("tipo"))
                );

                Comentario comentario = new Comentario(
                        rs.getString("texto"),
                        autor,
                        rs.getTimestamp("data_hora").toLocalDateTime()
                );

                comentarios.add(comentario);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return comentarios;
    }
}
