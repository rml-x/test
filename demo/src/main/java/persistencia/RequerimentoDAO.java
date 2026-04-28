package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import negocio.Aluno;
import negocio.Curso;
import negocio.Requerimento;
import negocio.TipoRequerimento;
import negocio.Usuario;

public class RequerimentoDAO implements IRequerimento {

    @Override

    public List<Requerimento>listarTodos() throws SQLException{
        
        List<Requerimento> lista = new ArrayList<Requerimento>();
        String sql = "SELECT r.*, u.nome AS nome_aluno, c.nome AS nome_curso, tr.descricao AS tipo_descricao FROM requerimento r INNER JOIN aluno a ON r.matricula = a.matricula INNER JOIN usuario u ON a.usuario_id = u.id INNER JOIN curso c ON a.curso_id = c.id INNER JOIN tipo_requerimento tr ON r.tipo_requerimento_id = tr.id WHERE r.ativo IS TRUE ORDER BY r.matricula ASC, r.data_hora_abertura DESC";

        try (Connection conn = new ConexaoPostgreSQL().getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Requerimento req = new Requerimento();

                req.setId(rs.getInt("id"));
                req.setObservacao(rs.getString("observacao"));
                req.setDataHoraEncerramento(rs.getTimestamp("data_hora_encerramento"));
                req.setDataHoraAbertura(rs.getTimestamp("data_hora_abertura"));
                req.setStatus(rs.getString("status"));
                
               
                Aluno aluno = new Aluno();
                aluno.setMatricula(rs.getString("matricula"));
                
                Usuario usu = new Usuario();
                usu.setNome(rs.getString("nome_aluno"));
                aluno.setUsuario(usu);

                Curso curso = new Curso();
                curso.setNome(rs.getString("nome_curso"));
                aluno.setCurso(curso);

                req.setAluno(aluno); // Adiciona o aluno ao requerimento

                TipoRequerimento tipo = new TipoRequerimento();
                tipo.setId(rs.getInt("tipo_requerimento_id"));
                tipo.setDescricao(rs.getString("tipo_descricao"));
                
                req.setTipo(tipo); // Adiciona o tipo ao requerimento

                lista.add(req);
            }
        }
        return lista;


    }

    //read
    public List<Requerimento> listarRequerimentoPorAluno(String matricula) throws SQLException {
        
        String sql = "SELECT r.*, u.nome AS nome_aluno, c.nome AS nome_curso, tr.descricao AS tipo_descricao FROM requerimento r INNER JOIN aluno a ON r.matricula = a.matricula INNER JOIN usuario u ON a.usuario_id = u.id INNER JOIN curso c ON a.curso_id = c.id INNER JOIN tipo_requerimento tr ON r.tipo_requerimento_id = tr.id WHERE r.ativo IS TRUE AND r.matricula = ? ORDER BY r.data_hora_abertura DESC;";

        List<Requerimento> reqPerAluno = new ArrayList<Requerimento>();

        try (Connection conn = new ConexaoPostgreSQL().getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, matricula);

            try (ResultSet rs = stmt.executeQuery()) { 
                while (rs.next()) {
                    Requerimento req = new Requerimento();
                    req.setId(rs.getInt("id"));
                    req.setObservacao(rs.getString("observacao"));
                    req.setDataHoraEncerramento(rs.getTimestamp("data_hora_encerramento"));
                    req.setDataHoraAbertura(rs.getTimestamp("data_hora_abertura"));
                    req.setStatus(rs.getString("status"));

                    // Aluno
                    Aluno aluno = new Aluno();
                    aluno.setMatricula(rs.getString("matricula"));
                    
                    Usuario usu = new Usuario();
                    usu.setNome(rs.getString("nome_aluno"));
                    aluno.setUsuario(usu);

                    Curso curso = new Curso();
                    curso.setNome(rs.getString("nome_curso"));
                    aluno.setCurso(curso);
                    req.setAluno(aluno);

                    // Tipo
                    TipoRequerimento tipo = new TipoRequerimento();
                    tipo.setId(rs.getInt("tipo_id"));
                    tipo.setDescricao(rs.getString("tipo_descricao"));
                    req.setTipo(tipo);

                    reqPerAluno.add(req);
                }
            }
        }
        return reqPerAluno;
    }
    

    public boolean atualizar(Requerimento requerimento) throws SQLException{

        String sql = "UPDATE requerimento SET data_hora_encerramento = ?, status = ? WHERE id = ?";

        try (Connection conn = new ConexaoPostgreSQL().getConexao();
            PreparedStatement instrucaoSQL = conn.prepareStatement(sql)) {
        
            instrucaoSQL.setTimestamp(1, requerimento.getDataHoraEncerramento());

            instrucaoSQL.setString(2, requerimento.getStatus());

            instrucaoSQL.setInt(3, requerimento.getId());

            int num = instrucaoSQL.executeUpdate();
            
            return num != 0; 
        } 

    }

    public void excluir(int id) throws SQLException{
    
    String sql = "UPDATE requerimento SET ativo = FALSE WHERE id = ?;"; 
    
    try (Connection conn = new ConexaoPostgreSQL().getConexao();
            PreparedStatement instrucaoSQL = conn.prepareStatement(sql)) {
                    
            instrucaoSQL.setInt(1, id); 
            instrucaoSQL.executeUpdate();
        }

    }

    //create
    public boolean abrirRequerimento(String matricula, int tipoId, String observacao) throws SQLException{

        String sql = "INSERT INTO requerimento (matricula, tipo_requerimento_id, observacao) VALUES (?, ?, ?)";

        try (Connection conn = new ConexaoPostgreSQL().getConexao();
        PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
    
            stmt.setString(1, matricula);
            stmt.setInt(2, tipoId); 
            stmt.setString(3, observacao);

            int rowsAffected = stmt.executeUpdate(); 

            if (rowsAffected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idGerado = rs.getInt(1);
                        System.out.println("Requerimento aberto com ID: " + idGerado);
                    }
                }
                return true;
                }
        }
        return false;

    }

}
