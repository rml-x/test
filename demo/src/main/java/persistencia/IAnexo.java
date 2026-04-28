package persistencia;

import java.sql.SQLException;
import java.util.List;

import negocio.Anexo;

public interface IAnexo {

    List<Anexo> listarTodos() throws SQLException;

    boolean salvar(Anexo anexo) throws SQLException;

    boolean atualizar(Anexo anexo) throws SQLException;

    void excluir(int id) throws SQLException;

    Anexo buscar(Anexo anexo) throws SQLException;
    
}
