package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoPostgreSQL {

    String url = "jdbc:postgresql://localhost:5432/sistema_requerimento"; 
    String usuario = "postgres";
    String senha = "postgres";

    public Connection getConexao() throws SQLException {

        System.out.println("Tentando conectar ao banco de dados...");

        try {
            
            Connection conexao = DriverManager.getConnection(url, usuario, senha);
            
            if (conexao != null) {
                System.out.println("---------------------------------------");
                System.out.println("SUCESSO: Conexão estabelecida!");
                System.out.println("---------------------------------------");
            }

            return conexao;

        } catch(SQLException e) {
            System.err.println("ERRO ao conectar:");
            
            if (e.getMessage().contains("password authentication failed")) {
                System.err.println("Causa: Senha incorreta para o usuário " + usuario);
            } else if (e.getMessage().contains("Connection refused")) {
                System.err.println("Causa: O serviço do PostgreSQL não está rodando ou a porta 5432 está fechada.");
            } else {
                e.printStackTrace();

            }

            throw e;
        }

    }
    
}
