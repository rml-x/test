package persistencia;

import java.util.List;

import negocio.Aluno;

public interface IAlunoDAO {

    void salvar(Aluno aluno);

    List<Aluno> listarTodos();

    Aluno buscarPorMatricula(String matricula);

    void atualizar(Aluno aluno);

    void excluir(String matricula);

    List<Aluno> listarPorCurso(int idCurso);

}