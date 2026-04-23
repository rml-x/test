package persistencia;

import java.util.List;

import negocio.Curso;

public interface ICursoDAO {

    void salvar(Curso curso);

    List<Curso> listarTodos();

    void atualizar(Curso curso);

    void excluir(int id);

    void obter(Curso curso);
}
