package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import negocio.Usuario;

public class UsuarioDAO implements IUsuario {

    @Overrride

    public void salvar(Usuario usuario){
        
    }

    public void excluir(int id) throws SQLException {
        
        String sql = "UPDATE usuario SET ativo = FALSE where id = ?;";  
        try (Connection conn = new ConexaoPostgreSQL().getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } 
    }
}



