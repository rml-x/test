package persistencia;
import java.sql.SQLException;
import java.util.List;

import negocio.Requerimento;

public interface IRequerimento {

    List<Requerimento>listarTodos() throws SQLException;

    Requerimento buscar(int id) throws SQLException;

    List<Requerimento>listarRequerimentoPorAluno(String matricula) throws SQLException;

    void atualizar(Requerimento requerimento) throws SQLException;

    void excluir(int id) throws SQLException;

    //create
    void abrirRequerimento(String matricula, int tipoId, String observacao) throws SQLException;

}