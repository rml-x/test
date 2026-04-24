package persistencia;

import java.sql.SQLException;
import java.util.List;

import negocio.Curso;

public interface ICurso {

    List<Curso> listarTodos() throws SQLException;

    boolean salvar(Curso curso) throws SQLException;

    boolean atualizar(Curso curso) throws SQLException;

    void excluir(int id) throws SQLException;

    Curso buscar(int id)throws SQLException;
}
