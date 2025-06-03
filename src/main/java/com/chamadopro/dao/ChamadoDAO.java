package com.chamadopro.dao;

import com.chamadopro.config.DatabaseConfig;
import com.chamadopro.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChamadoDAO {

    private static final ChamadoDAO instance = new ChamadoDAO();

    private ChamadoDAO() {}

    public static ChamadoDAO getInstance() {
        return instance;
    }

    public boolean salvar(Chamado chamado) {
        String sql = "INSERT INTO chamados (" +
                "titulo, descricao, status, categoria, data_hora_abertura, solicitante_id, responsavel_id, avaliacao, feedback" +
                ") VALUES (?, ?, ?::status_chamado, ?::categoria_problema, ?, ?, ?, ?, ?)";



        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, chamado.getTitulo());
            stmt.setString(2, chamado.getDescricao());
            stmt.setString(3, chamado.getStatus().name());
            stmt.setString(4, chamado.getCategoria().name());
            stmt.setTimestamp(5, Timestamp.valueOf(chamado.getDataHoraAbertura()));
            stmt.setInt(6, chamado.getSolicitante().getId());
            stmt.setObject(7, chamado.getResponsavel() != null ? chamado.getResponsavel().getId() : null);
            stmt.setObject(8, chamado.getAvaliacao());
            stmt.setString(9, chamado.getFeedback());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    chamado.setId(generatedKeys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Chamado> buscarPorSolicitante(Usuario usuario) {
        String sql = "SELECT * FROM chamados WHERE solicitante_id = ?";
        List<Chamado> lista = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuario.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(mapChamado(rs, usuario));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<Chamado> buscarPorResponsavel(Usuario tecnico) {
        String sql = "SELECT * FROM chamados WHERE responsavel_id = ?";
        List<Chamado> lista = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, tecnico.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Usuario solicitante = UsuarioDAO.getInstance().buscarTodos().stream()
                        .filter(u -> {
                            try {
                                return u.getId() == rs.getInt("solicitante_id");
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .findFirst().orElse(null);

                lista.add(mapChamado(rs, solicitante));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<Chamado> buscarTodos() {
        String sql = "SELECT * FROM chamados";
        List<Chamado> lista = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Usuario solicitante = UsuarioDAO.getInstance().buscarTodos().stream()
                        .filter(u -> {
                            try {
                                return u.getId() == rs.getInt("solicitante_id");
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .findFirst().orElse(null);

                lista.add(mapChamado(rs, solicitante));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void atualizarStatus(int idChamado, StatusChamado novoStatus) {
        String sql = "UPDATE chamados SET status = ?::status_chamado WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, novoStatus.name());
            stmt.setInt(2, idChamado);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean salvarAvaliacao(int idChamado, int avaliacao, String feedback) {
        String sql = "UPDATE chamados SET avaliacao = ?, feedback = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, avaliacao);
            stmt.setString(2, feedback);
            stmt.setInt(3, idChamado);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Chamado mapChamado(ResultSet rs, Usuario solicitante) throws SQLException {
        Chamado chamado = new Chamado();
        chamado.setId(rs.getInt("id"));
        chamado.setTitulo(rs.getString("titulo"));
        chamado.setDescricao(rs.getString("descricao"));
        chamado.setStatus(StatusChamado.valueOf(rs.getString("status")));
        chamado.setCategoria(CategoriaProblema.valueOf(rs.getString("categoria")));
        chamado.setSolicitante(solicitante);
        chamado.setDataHoraAbertura(rs.getTimestamp("data_hora_abertura").toLocalDateTime());
        chamado.setAvaliacao(rs.getObject("avaliacao", Integer.class));
        chamado.setFeedback(rs.getString("feedback"));

        int responsavelId = rs.getInt("responsavel_id");
        if (!rs.wasNull()) {
            Usuario responsavel = UsuarioDAO.getInstance().buscarTodos().stream()
                    .filter(u -> u.getId() == responsavelId)
                    .findFirst().orElse(null);
            chamado.setResponsavel(responsavel);
        }

        return chamado;
    }
}
