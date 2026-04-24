package persistencia;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import negocio.Usuario;


public class UsuarioDAO implements IUsuario {

    @Override

    public boolean salvar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario (nome, email, cpf, data_nascimento, cep, rua, complemento, nro) VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";

        try (Connection conn = new ConexaoPostgreSQL().getConexao();
            PreparedStatement instrucaoSQL = conn.prepareStatement(sql)) {

            instrucaoSQL.setString(1, usuario.getNome());
            instrucaoSQL.setString(2, usuario.getEmail());
            instrucaoSQL.setString(3, usuario.getCpf());
            
            if (usuario.getDataNascimento() != null) {
                instrucaoSQL.setDate(4, java.sql.Date.valueOf(usuario.getDataNascimento()));
            } else {
                instrucaoSQL.setNull(4, java.sql.Types.DATE);
            }

            instrucaoSQL.setString(5, usuario.getCep());
            instrucaoSQL.setString(6, usuario.getRua());
            instrucaoSQL.setString(7, usuario.getComplemento());
            instrucaoSQL.setString(8, usuario.getNro());

            try (ResultSet rs = instrucaoSQL.executeQuery()) {
                if (rs.next()) {
                    usuario.setId(rs.getInt("id"));
                    return true; 
                }
            }

        }

        return false; 
    }

    public void excluir(int id) throws SQLException {
        
        String sql = "UPDATE usuario SET ativo = FALSE where id = ?;";  
        try (Connection conn = new ConexaoPostgreSQL().getConexao();
            PreparedStatement instrucaoSQL = conn.prepareStatement(sql)) {
                
            instrucaoSQL.setInt(1, id);
            instrucaoSQL.executeUpdate();
        }
        
    }

    public List<Usuario> listarTodos() throws SQLException {

        List<Usuario> vetorUsuario = new ArrayList<Usuario>();
        String sql = "SELECT * FROM usuario WHERE ativo IS TRUE ORDER BY id ASC";
        
        try (Connection conn = new ConexaoPostgreSQL().getConexao();
            PreparedStatement instrucaoSQL = conn.prepareStatement(sql);
            ResultSet rs = instrucaoSQL.executeQuery();) {
                 
            while (rs.next()) {
                // enquanto ha resultados - crio instancias/objetos de usuario
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setDataNascimento((rs.getDate("data_nascimento") == null ) ? null: rs.getDate("data_nascimento").toLocalDate());
                usuario.setCpf(rs.getString("cpf"));
                usuario.setCep(rs.getString("cep"));
                usuario.setRua(rs.getString("rua"));
                usuario.setComplemento(rs.getString("complemento"));
                usuario.setNro(rs.getString("nro"));            
                // e coloco/acrescento como um novo elemento da colecao 
                vetorUsuario.add(usuario);
            }
        }
        
        return vetorUsuario;
    } 
    
    public boolean atualizar(Usuario usuario) throws SQLException {
        
        String sql = "UPDATE usuario SET nome = ?, email = ?, cpf = ?, data_nascimento = ?, cep = ?, rua = ?, complemento = ?, nro = ? where id = ?";

        try (Connection conn = new ConexaoPostgreSQL().getConexao();
            PreparedStatement instrucaoSQL = conn.prepareStatement(sql)) {

            instrucaoSQL.setString(1, usuario.getNome());
            instrucaoSQL.setString(2, usuario.getEmail());
            instrucaoSQL.setString(3, usuario.getCpf());        
            instrucaoSQL.setDate(4, (usuario.getDataNascimento() == null ) ? null: Date.valueOf(usuario.getDataNascimento()));
            instrucaoSQL.setString(5, usuario.getCep());
            instrucaoSQL.setString(6, usuario.getRua());
            instrucaoSQL.setString(7, usuario.getComplemento());
            instrucaoSQL.setString(8, usuario.getNro());
            instrucaoSQL.setInt(9, usuario.getId());
            
            int num = instrucaoSQL.executeUpdate();
            return num != 0; 
        }    

    }

    public Usuario buscar(int id) throws SQLException {
        
        // criei o sql
        String sql = "SELECT * FROM usuario WHERE ativo IS TRUE AND id = ? ORDER BY id ASC";
        
        try (Connection conn = new ConexaoPostgreSQL().getConexao();
            PreparedStatement instrucaoSQL = conn.prepareStatement(sql);) {

            instrucaoSQL.setInt(1, id);

            try (ResultSet rs = instrucaoSQL.executeQuery()) {

                if (rs.next()) {
                    Usuario usuario = new Usuario();

                    usuario.setId(rs.getInt("id"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setDataNascimento((rs.getDate("data_nascimento") == null ) ? null: rs.getDate("data_nascimento").toLocalDate());
                    usuario.setCpf(rs.getString("cpf"));
                    usuario.setCep(rs.getString("cep"));
                    usuario.setRua(rs.getString("rua"));
                    usuario.setComplemento(rs.getString("complemento"));
                    usuario.setNro(rs.getString("nro")); 

                    return usuario; 
                } 
                
            }
              
        }
        return null;

    }

}   