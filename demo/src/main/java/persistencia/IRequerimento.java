package persistencia;

import java.util.List;

import negocio.Requerimento;
import negocio.TipoRequerimento;

public interface IRequerimento {

    List<Requerimento>listarTodos();

    void buscarPorId(int id);

    List<Requerimento>listarPorAluno(String matricula);

    void salvar(Requerimento requerimento);

    void atualizar(Requerimento requerimento);

    void excluir(Requerimento requerimento);

    void abrirRequerimento(String matricula, int tipoId, TipoRequerimento tipo, String observacao);

}