package persistencia;

import java.util.List;

import negocio.Aluno;

public interface IAluno {

    List<Aluno> listarTodos();

    void salvar(Aluno aluno);

    Aluno buscar(String matricula);

    void atualizar(Aluno aluno);

    void excluir(String matricula);

    List<Aluno> listarPorCurso(int idCurso);

}