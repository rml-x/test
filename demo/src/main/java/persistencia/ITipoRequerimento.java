package persistencia;
import java.sql.SQLException;
import java.util.List;

import negocio.TipoRequerimento;

public interface ITipoRequerimento {

    boolean salvar(TipoRequerimento tipo) throws SQLException;

    List<TipoRequerimento> listarTodos() throws SQLException;

    boolean atualizar(TipoRequerimento tipo) throws SQLException;

    void excluir(int id) throws SQLException;

    TipoRequerimento buscar(int id) throws SQLException;
    
}
