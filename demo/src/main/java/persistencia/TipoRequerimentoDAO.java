package persistencia;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import negocio.TipoRequerimento;


public class TipoRequerimentoDAO implements ITipoRequerimento {

    
    @Override

    public boolean salvar(TipoRequerimento tipo) throws SQLException {
        String sql = "INSERT INTO tipo_requerimento (descricao) VALUES (?) RETURNING id";

        try (Connection conn = new ConexaoPostgreSQL().getConexao();
            PreparedStatement instrucaoSQL = conn.prepareStatement(sql)) {

            instrucaoSQL.setString(1, tipo.getDescricao());

            try (ResultSet rs = instrucaoSQL.executeQuery()) {
                if (rs.next()) {
                    tipo.setId(rs.getInt("id"));
                    return true; 
                }
            }

        }

        return false; 
    }

    public void excluir(int id) throws SQLException {
        
        String sql = "UPDATE tipo_requerimento SET ativo = FALSE where id = ?;";  
        try (Connection conn = new ConexaoPostgreSQL().getConexao();
            PreparedStatement instrucaoSQL = conn.prepareStatement(sql)) {
                
            instrucaoSQL.setInt(1, id);
            instrucaoSQL.executeUpdate();
        }
        
    }

    public List<TipoRequerimento> listarTodos() throws SQLException {

        List<TipoRequerimento> vetorRequerimento = new ArrayList<TipoRequerimento>();
        String sql = "SELECT * FROM tipo_requerimento WHERE ativo IS TRUE ORDER BY id ASC";
        
        try (Connection conn = new ConexaoPostgreSQL().getConexao();
            PreparedStatement instrucaoSQL = conn.prepareStatement(sql);
            ResultSet rs = instrucaoSQL.executeQuery();) {
                 
            while (rs.next()) {
                // enquanto ha resultados - crio instancias/objetos de usuario
                TipoRequerimento  tipo = new TipoRequerimento();
                
                tipo.setId(rs.getInt("id"));
                tipo.setDescricao(rs.getString("descricao"));
                // e coloco/acrescento como um novo elemento da colecao 
                vetorRequerimento.add(tipo);
            }
        }
        
        return vetorRequerimento;
    } 
    
    public boolean atualizar(TipoRequerimento tipo) throws SQLException {
        
        String sql = "UPDATE tipo_requerimento SET descricao = ? where id = ?";

        try (Connection conn = new ConexaoPostgreSQL().getConexao();
            PreparedStatement instrucaoSQL = conn.prepareStatement(sql)) {
            
            instrucaoSQL.setString(1, tipo.getDescricao());
            instrucaoSQL.setInt(2, tipo.getId());

            int num = instrucaoSQL.executeUpdate();
            return num != 0; 
        }    

    }

    public TipoRequerimento buscar(int id) throws SQLException {
        
        // criei o sql
        String sql = "SELECT * FROM tipo_requerimento WHERE ativo IS TRUE AND id = ? ORDER BY id ASC";
        
        try (Connection conn = new ConexaoPostgreSQL().getConexao();
            PreparedStatement instrucaoSQL = conn.prepareStatement(sql);) {

            instrucaoSQL.setInt(1, id);

            try (ResultSet rs = instrucaoSQL.executeQuery()) {

                if (rs.next()) {
                    TipoRequerimento tipo = new TipoRequerimento();

                    tipo.setId(rs.getInt("id"));
                    tipo.setDescricao(rs.getString("descricao"));
                    return tipo; 
                } 
                
            }
              
        }
        return null;

    }

}