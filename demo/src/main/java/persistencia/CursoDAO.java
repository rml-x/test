package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import negocio.Curso;


public class CursoDAO implements ICurso {
    
    @Override

    public List<Curso> listarTodos() throws SQLException {

        List<Curso> vetorCurso = new ArrayList<Curso>();
        String sql = "SELECT * FROM curso WHERE ativo IS TRUE ORDER BY id ASC";
        
        try (Connection conn = new ConexaoPostgreSQL().getConexao();
            PreparedStatement instrucaoSQL = conn.prepareStatement(sql);
            ResultSet rs = instrucaoSQL.executeQuery();) {
                 
            while (rs.next()) {
                // enquanto ha resultados - crio instancias/objetos de curso
                Curso curso = new Curso();

                curso.setId(rs.getInt("id"));
                curso.setNome(rs.getString("nome"));
                curso.setSite(rs.getString("site"));
                curso.setDuracao(rs.getInt("duracao"));
                curso.setTurno(rs.getString("turno"));          
                // e coloco/acrescento como um novo elemento da colecao 
                
                vetorCurso.add(curso);
            }
        }
        
        return vetorCurso;

    }

    public boolean salvar(Curso curso) throws SQLException {

        String sql = "INSERT INTO curso (nome, site, turno, duracao) VALUES (?, ?, ?, ?) RETURNING id";

        try (Connection conn = new ConexaoPostgreSQL().getConexao();
            PreparedStatement instrucaoSQL = conn.prepareStatement(sql)) {

            instrucaoSQL.setString(1, curso.getNome());
            instrucaoSQL.setString(2, curso.getSite());
            instrucaoSQL.setString(3, curso.getTurno());
            instrucaoSQL.setInt(4, curso.getDuracao());

            try (ResultSet rs = instrucaoSQL.executeQuery()) {
                if (rs.next()) {
                    curso.setId(rs.getInt("id"));
                    return true; 
                }
            }

        }

        return false; 
    
    }

    public boolean atualizar(Curso curso) throws SQLException {

        String sql = "UPDATE curso SET nome = ?, site = ?, turno = ?, duracao = ? where id = ?";

        try (Connection conn = new ConexaoPostgreSQL().getConexao();
            PreparedStatement instrucaoSQL = conn.prepareStatement(sql)) {

            instrucaoSQL.setString(1, curso.getNome());
            instrucaoSQL.setString(2, curso.getSite());
            instrucaoSQL.setString(3, curso.getTurno());
            instrucaoSQL.setInt(4, curso.getDuracao());
            instrucaoSQL.setInt(5, curso.getId());
            
            int num = instrucaoSQL.executeUpdate();
            return num != 0; 
        }    

    }

    public void excluir(int id) throws SQLException {
    
        String sql = "UPDATE curso SET ativo = FALSE where id = ?;";
        
        try (Connection conn = new ConexaoPostgreSQL().getConexao();
            PreparedStatement instrucaoSQL = conn.prepareStatement(sql)) {
                
            instrucaoSQL.setInt(1, id);
            instrucaoSQL.executeUpdate();
        }

    }

    public Curso buscar(int id)throws SQLException{
        
        String sql = "SELECT * FROM curso WHERE ativo IS TRUE AND id = ? ORDER BY id ASC";
        
        try (Connection conn = new ConexaoPostgreSQL().getConexao();
           
            PreparedStatement instrucaoSQL = conn.prepareStatement(sql);) {

            instrucaoSQL.setInt(1, id);

            try (ResultSet rs = instrucaoSQL.executeQuery()) {

                if (rs.next()) {

                    Curso curso = new Curso();

                    curso.setId(rs.getInt("id"));
                    curso.setNome(rs.getString("nome"));
                    curso.setSite(rs.getString("site"));
                    curso.setDuracao(rs.getInt("duracao"));
                    curso.setTurno(rs.getString("turno")); 
                    
                    return curso; 
                } 
                
            }
              
        }
        return null;

    }
}
