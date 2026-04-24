package persistencia;

import java.sql.SQLException;
import java.util.List;

import negocio.Usuario;

public interface IUsuario {

    void salvar(Usuario usuario);

    List<Usuario> listarTodos();

    void atualizar(Usuario usuario);

    void excluir(int id) throws SQLException;

    Usuario buscar(Usuario usuario);
    
}
