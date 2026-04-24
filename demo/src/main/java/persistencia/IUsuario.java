package persistencia;

import java.sql.SQLException;
import java.util.List;

import negocio.Usuario;

public interface IUsuario {

    boolean salvar(Usuario usuario) throws SQLException;

    List<Usuario> listarTodos() throws SQLException;

    boolean atualizar(Usuario usuario) throws SQLException;

    void excluir(int id) throws SQLException;

    Usuario buscar(int id) throws SQLException;
    
}
