package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import negocio.Requerimento;

public class RequerimentoDAO implements IRequerimento {

    @Override

    public List<Requerimento>listarTodos() throws SQLException{


    }

    public Requerimento buscar(int id) throws SQLException{

    }

    public List<Requerimento>listarRequerimentoPorAluno(String matricula) throws SQLException{

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
