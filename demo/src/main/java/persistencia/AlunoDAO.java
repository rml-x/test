package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import negocio.Aluno;
import negocio.Curso;
import negocio.Usuario;

public class AlunoDAO implements IAluno {

    @Override

    public List<Aluno> listarTodos() throws SQLException {

        List<Aluno> vetorAluno = new ArrayList<Aluno>();
        String sql = "SELECT a.*, u.nome, u.email, u.cpf, c.nome AS nome_curso FROM aluno a INNER JOIN usuario u ON a.usuario_id = u.id INNER JOIN curso c ON a.curso_id = c.id WHERE a.ativo IS TRUE ORDER BY u.nome ASC";

        try (Connection conn = new ConexaoPostgreSQL().getConexao();
            PreparedStatement instrucaoSQL = conn.prepareStatement(sql);
            ResultSet rs = instrucaoSQL.executeQuery();) {
                 
            while (rs.next()) {

                Aluno aluno = new Aluno();
                aluno.setMatricula(rs.getString("matricula"));
                aluno.setStatus(rs.getString("status"));

                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("usuario_id"));
                usuario.setNome(rs.getString("nome"));     
                usuario.setEmail(rs.getString("email"));
                usuario.setCpf(rs.getString("cpf"));

                Curso curso = new Curso();
                curso.setId(rs.getInt("curso_id"));  
                curso.setNome(rs.getString("nome_curso")); 
                
                aluno.setUsuario(usuario);
                aluno.setCurso(curso);

                vetorAluno.add(aluno);
            }
            
        }
        
        return vetorAluno ;

    }

    public boolean salvar(Aluno aluno)  throws SQLException {
        String sql = "INSERT INTO aluno (matricula, usuario_id, curso_id) VALUES (?, ?, ?)";

        try (Connection conn = new ConexaoPostgreSQL().getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, aluno.getMatricula());
            
            // Pegamos o ID de dentro do objeto Usuario que está dentro do Aluno
            stmt.setInt(2, aluno.getUsuario().getId()); 
            
            // Pegamos o ID de dentro do objeto Curso que está dentro do Aluno
            stmt.setInt(3, aluno.getCurso().getId());
            

            stmt.executeUpdate();
            return true;
        }

    }

    public Aluno buscar(String matricula) throws SQLException {
        String sql = "SELECT a.*, u.nome, u.email, u.cpf, c.nome AS nome_curso FROM aluno a INNER JOIN usuario u ON a.usuario_id = u.id INNER JOIN curso c ON a.curso_id = c.id WHERE a.matricula = ? AND a.ativo IS TRUE";

        try (Connection conn = new ConexaoPostgreSQL().getConexao();
            PreparedStatement instrucaoSQL = conn.prepareStatement(sql);) {

            instrucaoSQL.setString(1, matricula);
                
            try (ResultSet rs = instrucaoSQL.executeQuery()) {

                if (rs.next()) {
                    
                    Aluno aluno = new Aluno();
                    aluno.setMatricula(rs.getString("matricula"));
                    aluno.setStatus(rs.getString("status"));

                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("usuario_id"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setCpf(rs.getString("cpf"));
                    aluno.setUsuario(usuario);

                    Curso curso = new Curso();
                    curso.setId(rs.getInt("curso_id"));
                    curso.setNome(rs.getString("nome_curso"));
                    aluno.setCurso(curso);

                    return aluno;
                    
                } 
                
            }
              
        }
        return null;

    }

    public boolean atualizar(Aluno aluno) throws SQLException {
        String sql = "UPDATE aluno SET status = ?, curso_id = ? WHERE matricula = ?";

        try (Connection conn = new ConexaoPostgreSQL().getConexao();
            PreparedStatement instrucaoSQL = conn.prepareStatement(sql)) {
            
            instrucaoSQL.setString(1, aluno.getStatus());

            instrucaoSQL.setInt(2, aluno.getCurso().getId());

            instrucaoSQL.setString(3, aluno.getMatricula());

            int num = instrucaoSQL.executeUpdate();
            
            return num != 0; 
        }    
    }

    public void excluir(String matricula) throws SQLException {
        String sql = "UPDATE aluno SET ativo = FALSE WHERE matricula = ?;"; 
        try (Connection conn = new ConexaoPostgreSQL().getConexao();
            PreparedStatement instrucaoSQL = conn.prepareStatement(sql)) {
                    
            instrucaoSQL.setString(1, matricula); 
            instrucaoSQL.executeUpdate();
        }
    }

    public List<Aluno> listarPorCurso(int idCurso) throws SQLException {

        List<Aluno> vetorAlunoCurso = new ArrayList<Aluno>();
        String sql = "SELECT a.*, u.nome, u.email, u.cpf, c.nome AS nome_curso FROM aluno a INNER JOIN usuario u ON a.usuario_id = u.id INNER JOIN curso c ON a.curso_id = c.id WHERE a.curso_id = ? AND a.ativo IS TRUE ORDER BY u.nome ASC";
    
        try (Connection conn = new ConexaoPostgreSQL().getConexao();
        PreparedStatement instrucaoSQL = conn.prepareStatement(sql)) {

            instrucaoSQL.setInt(1, idCurso);

            try (ResultSet rs = instrucaoSQL.executeQuery()) {
                while (rs.next()) {
                    Aluno aluno = new Aluno();
                    aluno.setMatricula(rs.getString("matricula"));
                    aluno.setStatus(rs.getString("status"));

                    // Objeto Usuário
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("usuario_id"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setCpf(rs.getString("cpf"));
                    aluno.setUsuario(usuario);

                    // Objeto Curso
                    Curso curso = new Curso();
                    curso.setId(rs.getInt("curso_id"));
                    curso.setNome(rs.getString("nome_curso"));
                    aluno.setCurso(curso);

                    vetorAlunoCurso.add(aluno);
                }
            }
        }
        return vetorAlunoCurso;
    }
    
}
