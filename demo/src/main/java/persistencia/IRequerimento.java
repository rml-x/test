package persistencia;

import java.util.List;

import negocio.Requerimento;

public interface IRequerimento {

    List<Requerimento>listarTodos();

    Requerimento buscar(int id);

    List<Requerimento>listarRequerimentoPorAluno(String matricula);

    void atualizar(Requerimento requerimento);

    void excluir(int id);

    //create
    void abrirRequerimento(String matricula, int tipoId, String observacao);

}