package persistencia;
import java.sql.SQLException;
import java.util.List;

import negocio.TipoRequerimento;

public interface ITipoRequerimento {

    void salvar(TipoRequerimento tipo);

    List<TipoRequerimento> listarTodos();

    void atualizar(TipoRequerimento tipo);

    void excluir(int id);

    TipoRequerimento buscar(TipoRequerimento tipo);
    
}
