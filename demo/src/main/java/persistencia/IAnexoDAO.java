package persistencia;

import java.util.List;

import negocio.Anexo;

public interface IAnexoDAO {

    List<Anexo> listarTodos();

    void salvar(Anexo anexo);

    void atualizar(Anexo anexo);

    void excluir(int id);

    Anexo buscar(Anexo anexo);
    
}
