package apresentacao;

import java.sql.SQLException;
import java.time.LocalDate;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinMustache;

import java.util.HashMap;
import java.util.Map;

import negocio.Curso;
import negocio.Usuario;
import persistencia.CursoDAO;
import persistencia.UsuarioDAO;

public class Main {
    public static void main(String[] args) throws SQLException {
        // ------------------------------------
        
        // var app = 
        Javalin.create(config -> {
            // define qual vai ser a minha engine de templates
            config.fileRenderer(new JavalinMustache());    

            // defino as minhas rotas

            // a unica rota que tenha eh a index
            config.routes.get("/", ctx -> {
                // crio um map <chave, valor> para que seja usado la no html
                Map<String, Object> map = new HashMap<>();
                // defino um apelido para a colecao de objetos de curso vindos do banco
                map.put("vetorCurso", new CursoDAO().listarTodos());

                map.put("teste", "oi!! igor paraninfo!");
                // renderizo a pagina html encaminhando tb o map
                ctx.render("/templates/index.html", map);
            });

           


            // defino que minha aplicacao rodara na porta 7070
        }).start(7070);
    }
}