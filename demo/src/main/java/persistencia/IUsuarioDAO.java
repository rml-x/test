package persistencia;

import java.util.List;

import negocio.Usuario;

public class IUsuarioDAO {

    void salvar(Usuario usuario);

    List<Usuario> listarTodos();

    void atualizar(Usuario usuario);

    void excluir(int id);

    void obter(Usuario usuario);
    
}
