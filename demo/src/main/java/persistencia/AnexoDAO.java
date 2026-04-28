package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import negocio.Anexo;
import negocio.Requerimento;

public class AnexoDAO implements IAnexo {

    @Override

    public boolean salvar(Anexo anexo) throws SQLException {
        String sql = "INSERT INTO anexo (descricao, arquivo, requerimento_id) VALUES (?, ?, ?) RETURNING id";

        try (Connection conn = new ConexaoPostgreSQL().getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, anexo.getDescricao());
            stmt.setBytes(2, anexo.getArquivo());
            stmt.setInt(3, anexo.getRequerimento().getId());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    anexo.setId(rs.getInt("id"));
                    return true;
                }
            }
        }
        return false;
    }

    @Override

    public List<Anexo> listarTodos() throws SQLException {
        List<Anexo> lista = new ArrayList<>();
        String sql = "SELECT * FROM anexo ORDER BY id DESC";

        try (Connection conn = new ConexaoPostgreSQL().getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearAnexo(rs));
            }
        }
        return lista;
    }

    @Override

    public boolean atualizar(Anexo anexo) throws SQLException {
        String sql = "UPDATE anexo SET descricao = ?, arquivo = ? WHERE id = ?";

        try (Connection conn = new ConexaoPostgreSQL().getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, anexo.getDescricao());
            stmt.setBytes(2, anexo.getArquivo());
            stmt.setInt(3, anexo.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    @Override

    public void excluir(int id) throws SQLException {
        String sql = "UPDATE anexo SET ativo = FALSE WHERE id = ?";

        try (Connection conn = new ConexaoPostgreSQL().getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override

    public Anexo buscar(Anexo anexo) throws SQLException {
        String sql = "SELECT * FROM anexo WHERE id = ?";
        
        try (Connection conn = new ConexaoPostgreSQL().getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, anexo.getId());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearAnexo(rs);
                }
            }
        }
        return null;
    }

    private Anexo mapearAnexo(ResultSet rs) throws SQLException {
        Anexo a = new Anexo();
        a.setId(rs.getInt("id"));
        a.setDescricao(rs.getString("descricao"));
        a.setArquivo(rs.getBytes("arquivo")); 

        Requerimento req = new Requerimento();
        req.setId(rs.getInt("requerimento_id"));
        a.setRequerimento(req);

        return a;
    }
}