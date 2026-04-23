package persistencia;

import java.util.List;

import negocio.Anexo;

public interface IAnexoDAO {

    void salvar(Anexo anexo);

    List<Anexo> listarTodos();

    void atualizar(Anexo anexo);

    void excluir(int id);

    void obter(Anexo anexo);
    
}
