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
        String sql = "SELECT r.*, u.nome AS nome_aluno, c.nome AS nome_curso, tr.observacao AS obs FROM requerimento r INNER JOIN aluno a ON r.matricula_aluno = a.matricula INNER JOIN usuario u ON a.usuario_id = u.id INNER JOIN curso c ON a.curso_id = c.id INNER JOIN tipo_requerimento tr ON r.tipo_id = tr.id WHERE r.ativo IS TRUE ORDER BY r.id DESC";

        try (Connection conn = new ConexaoPostgreSQL().getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Requerimento req = new Requerimento();

                req.setId(rs.getInt("id"));
                req.setObservacao(rs.getString("observacao"));
                req.setDataHoraAbertura(rs.getTimestamp("data_hora_abertura"));
                req.setStatus(rs.getString("status"));
                
               
                Aluno aluno = new Aluno();
                aluno.setMatricula(rs.getString("matricula_aluno"));
                
                Usuario usu = new Usuario();
                usu.setNome(rs.getString("nome_aluno"));
                aluno.setUsuario(usu);

                Curso curso = new Curso();
                curso.setNome(rs.getString("nome_curso"));
                aluno.setCurso(curso);

                req.setAluno(aluno); // Adiciona o aluno ao requerimento

                TipoRequerimento tipo = new TipoRequerimento();
                tipo.setId(rs.getInt("tipo_id"));
                tipo.setDescricao(rs.getString("tipo_descricao"));
                
                req.setTipo(tipo); // Adiciona o tipo ao requerimento

                lista.add(req);
            }
        }
        return lista;


    }

    public Requerimento buscar(int id) throws SQLException{

    }

    public List<Requerimento> listarRequerimentoPorAluno(String matricula) throws SQLException {
        
        String sql = "SELECT r.*, u.nome AS nome_aluno, c.nome AS nome_curso, tr.descricao AS tipo_descricao FROM requerimento r INNER JOIN aluno a ON r.matricula_aluno = a.matricula INNER JOIN usuario u ON a.usuario_id = u.id INNER JOIN curso c ON a.curso_id = c.id INNER JOIN tipo_requerimento tr ON r.tipo_id = tr.id WHERE r.ativo IS TRUE AND r.matricula_aluno = ? ORDER BY r.id DESC";

        List<Requerimento> reqPerAluno = new ArrayList<>();

        try (Connection conn = new ConexaoPostgreSQL().getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, matricula);

            try (ResultSet rs = stmt.executeQuery()) { 
                while (rs.next()) {
                    Requerimento req = new Requerimento();
                    req.setId(rs.getInt("id"));
                    req.setObservacao(rs.getString("observacao"));
                    req.setDataHoraAbertura(rs.getTimestamp("data_hora_abertura"));
                    req.setStatus(rs.getString("status"));

                    // Aluno
                    Aluno aluno = new Aluno();
                    aluno.setMatricula(rs.getString("matricula_aluno"));
                    
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
    

    public void atualizar(Requerimento requerimento) throws SQLException{

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
    public void abrirRequerimento(String matricula, int tipoId, String observacao) throws SQLException{

    }

}
