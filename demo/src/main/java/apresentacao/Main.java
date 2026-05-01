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
            
            //CURSOS-------------------------------------------------------------------------------
            config.routes.get("/", ctx -> {
                // crio um map <chave, valor> para que seja usado la no html
                Map<String, Object> map = new HashMap<>();
                // defino um apelido para a colecao de objetos de curso vindos do banco
                map.put("vetorCurso", new CursoDAO().listarTodos());

                //map.put("teste", "oi!! igor paraninfo!");
                // renderizo a pagina html encaminhando tb o map
                ctx.render("/templates/index.html", map);
            }); 

            config.routes.get("/curso/excluir/{id}", ctx -> {
                new CursoDAO().excluir(Integer.parseInt(ctx.pathParam("id")));
                ctx.redirect("/");
            });

            config.routes.get("/curso/tela_adicionar", ctx -> {
                ctx.render("/templates/curso/tela_adicionar.html");
            });

             config.routes.post("/curso/adicionar", ctx -> {
                String nome = ctx.formParam("nome");
                String site = ctx.formParam("site");
                String turno = ctx.formParam("turno");
                int duracao = Integer.parseInt(ctx.formParam("duracao"));
                Curso curso = new Curso();
                curso.setNome(nome);
                curso.setSite(site);
                curso.setTurno(turno);
                curso.setDuracao(duracao);
                if (new CursoDAO().salvar(curso)) {
                    ctx.redirect("/");
                } else {
                    ctx.redirect("/templates/curso/tela_adicionar.html");
                }
                // ctx.render("/templates/curso/tela_adicionar.html");
            });


            config.routes.get("/curso/tela_alterar/{id}", ctx -> {
                Curso curso = new CursoDAO().buscar(Integer.parseInt(ctx.pathParam("id")));
                Map<String, Object> map = new HashMap<>();
                // defino um apelido para a colecao de objetos de curso vindos do banco
                map.put("curso", curso);
                ctx.render("/templates/curso/tela_alterar.html", map);
            });

            config.routes.post("/curso/tela_alterar", ctx -> {
                int id = Integer.parseInt(ctx.formParam("id"));
                String nome = ctx.formParam("nome");
                String site = ctx.formParam("site");
                String turno = ctx.formParam("turno");
                int duracao = Integer.parseInt(ctx.formParam("duracao"));
                Curso curso = new Curso();
                curso.setId(id);
                curso.setNome(nome);
                curso.setSite(site);
                curso.setTurno(turno);
                curso.setDuracao(duracao);

                Map<String, Object> map = new HashMap<>();

                if (new CursoDAO().atualizar(curso)) {
                    ctx.redirect("/");
                } else {
                    // defino um apelido para a colecao de objetos de curso vindos do banco
                    map.put("curso", curso);
                    ctx.render("/templates/curso/tela_alterar.html", map);
                }
                // ctx.render("/templates/curso/tela_adicionar.html");
            });

            //USUARIOS---------------------------------------------------------------------------------------------------
           
            config.routes.get("/usuarios", ctx -> {
                // crio um map <chave, valor> para que seja usado la no html
                Map<String, Object> map = new HashMap<>();
                // defino um apelido para a colecao de objetos de curso vindos do banco
                map.put("vetorUsuario", new UsuarioDAO().listarTodos());                
                // renderizo a pagina html encaminhando tb o map
                ctx.render("/templates/usuario/index.html", map);
            });

             config.routes.get("/usuario/excluir/{id}", ctx -> {
                new UsuarioDAO().excluir(Integer.parseInt(ctx.pathParam("id")));
                ctx.redirect("/usuarios");
            });
            

            //VOLTAR=======================================================================
            config.routes.get("/usuarios/return", ctx -> {
               
                ctx.redirect("/");
            });

            
            config.routes.get("/usuario/tela_adicionar", ctx -> {
                ctx.render("/templates/usuario/tela_adicionar.html");
            });

             config.routes.post("/usuario/tela_adicionar", ctx -> {
                String nome = ctx.formParam("nome");
                String email = ctx.formParam("email");
                String cpf = ctx.formParam("cpf");
                String dataNascimento = ctx.formParam("data_nascimento");
                String cep = ctx.formParam("cep");
                String rua = ctx.formParam("rua");
                String complemento = ctx.formParam("complemento");
                String nro = ctx.formParam("nro");                               

                Usuario usuario = new Usuario();
                usuario.setNome(nome);
                usuario.setEmail(email);
                System.out.println(dataNascimento);
                usuario.setDataNascimento(LocalDate.parse(dataNascimento));
                usuario.setCpf(cpf);
                usuario.setCep(cep);
                usuario.setRua(rua);
                usuario.setNro(nro);
                usuario.setComplemento(complemento);

                if (new UsuarioDAO().salvar(usuario)) {
                    ctx.redirect("/usuarios");
                } else {
                    ctx.redirect("/templates/usuario/tela_adicionar.html");
                }
                // ctx.render("/templates/curso/tela_adicionar.html");
            });

          
            config.routes.get("/usuario/tela_alterar/{id}", ctx -> {
                Curso curso = new CursoDAO().buscar(Integer.parseInt(ctx.pathParam("id")));
                Map<String, Object> map = new HashMap<>();
                // defino um apelido para a colecao de objetos de curso vindos do banco
                map.put("curso", curso);
                ctx.render("/templates/usuario/tela_alterar.html", map);
            });

            config.routes.post("//tela_alterar", ctx -> {
                int id = Integer.parseInt(ctx.formParam("id"));
                String nome = ctx.formParam("nome");
                String site = ctx.formParam("site");
                String turno = ctx.formParam("turno");
                int duracao = Integer.parseInt(ctx.formParam("duracao"));
                Curso curso = new Curso();
                curso.setId(id);
                curso.setNome(nome);
                curso.setSite(site);
                curso.setTurno(turno);
                curso.setDuracao(duracao);

                Map<String, Object> map = new HashMap<>();

                if (new CursoDAO().atualizar(curso)) {
                    ctx.redirect("/");
                } else {
                    // defino um apelido para a colecao de objetos de curso vindos do banco
                    map.put("curso", curso);
                    ctx.render("/templates/curso/tela_alterar.html", map);
                }
                // ctx.render("/templates/curso/tela_adicionar.html");
            });


            */



           


            // defino que minha aplicacao rodara na porta 7070
        }).start(7070);
    }
}