package persistencia;

import java.util.List;

import negocio.Curso;

public interface ICursoDAO {

    List<Curso> listarTodos();

    void salvar(Curso curso);

    void atualizar(Curso curso);

    void excluir(int id);

    Curso buscar(Curso curso);
}
