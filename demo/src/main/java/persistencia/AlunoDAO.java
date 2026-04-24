package persistencia;

import java.sql.SQLException;
import java.util.List;

import negocio.Aluno;

public class AlunoDAO implements IAluno {

    @Override

    List<Aluno> listarTodos() throws SQLException {

    }

    boolean salvar(Aluno aluno)  throws SQLException {

    }

    Aluno buscar(String matricula) throws SQLException {

    }

    boolean atualizar(Aluno aluno) throws SQLException {

    }

    void excluir(String matricula) throws SQLException {

    }

    List<Aluno> listarPorCurso(int idCurso) throws SQLException {
        
    }
    
}
