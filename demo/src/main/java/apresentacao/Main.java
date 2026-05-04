package apresentacao;

import java.sql.SQLException;
import java.time.LocalDate;

import io.javalin.Javalin;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.UploadedFile;
import io.javalin.rendering.template.JavalinMustache;

import java.util.HashMap;
import java.util.Map;

import negocio.Aluno;
import negocio.Anexo;
import negocio.Curso;
import negocio.Requerimento;
import negocio.TipoRequerimento;
import negocio.Usuario;
import persistencia.AlunoDAO;
import persistencia.AnexoDAO;
import persistencia.CursoDAO;
import persistencia.RequerimentoDAO;
import persistencia.TipoRequerimentoDAO;
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
            
            //CURSOS==================================================================================
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

            //VOLTAR=======================================================================
            config.routes.get("/curso/return", ctx -> {
               
                ctx.redirect("/");
            });



            config.routes.get("/curso/tela_alterar/{id}", ctx -> {
                Curso curso = new CursoDAO().buscar(Integer.parseInt(ctx.pathParam("id")));
                Map<String, Object> map = new HashMap<>();
                // defino um apelido para a colecao de objetos de curso vindos do banco
                map.put("curso", curso);
                ctx.render("/templates/curso/tela_alterar.html", map);
            });

            //VOLTAR=======================================================================
            config.routes.get("/curso/tela_alterar/return", ctx -> {
               
                ctx.redirect("/");
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
                String senha = ctx.formParam("senha");
                String cpf = ctx.formParam("cpf");
                String cep = ctx.formParam("cep");
                String dataNascimento = ctx.formParam("data_nascimento");
                String rua = ctx.formParam("rua");
                String complemento = ctx.formParam("complemento");
                String nro = ctx.formParam("nro");                               

                Usuario usuario = new Usuario();
                usuario.setNome(nome);
                usuario.setEmail(email);
                usuario.setSenha(senha);
                System.out.println(dataNascimento);
                usuario.setDataNascimento(LocalDate.parse(dataNascimento));
                usuario.setCep(cep);
                usuario.setCpf(cpf);
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
                Usuario usuario = new UsuarioDAO().buscar(Integer.parseInt(ctx.pathParam("id")));
                Map<String, Object> map = new HashMap<>();

                map.put("usuario", usuario);
                ctx.render("/templates/usuario/tela_alterar.html", map);
            });

            config.routes.post("/usuario/tela_alterar", ctx -> {

                int id = ctx.formParamAsClass("id", Integer.class).getOrThrow(e -> new BadRequestResponse("ID inválido ou ausente"));

                String nome = ctx.formParam("nome");
                String cpf = ctx.formParam("cpf");
                String email = ctx.formParam("email");
                String cep = ctx.formParam("cep");
                String rua = ctx.formParam("rua");
                String complemento = ctx.formParam("complemento");  
                String nro = ctx.formParam("nro");
                String manter_senha = ctx.formParam("manter_senha");
                // System.out.println(manter_senha);
                String senha = ctx.formParam("senha");

                String dataStr = ctx.formParam("data_nascimento");
                LocalDate date = (dataStr != null && !dataStr.isEmpty()) ? LocalDate.parse(dataStr) : null;

                Usuario usuario = new Usuario();
                boolean manter_senha_boolean = true;
                if (manter_senha != null && manter_senha.equals("manter")) {
                    usuario = new UsuarioDAO().buscar(id);
                }
                else if (manter_senha == null){
                    manter_senha_boolean = false;
                    usuario.setSenha(senha);
                }
                usuario.setId(id);
                usuario.setNome(nome);
                usuario.setCpf(cpf);
                usuario.setEmail(email);
                usuario.setCep(cep);
                usuario.setComplemento(complemento);
                usuario.setRua(rua);
                usuario.setNro(nro);
                usuario.setDataNascimento(date);

                Map<String, Object> map = new HashMap<>();
                if (new UsuarioDAO().atualizar(usuario, manter_senha_boolean)) {
                    ctx.redirect("/usuarios");
                } else {
                    // defino um apelido para a colecao de objetos de curso vindos do banco
                    map.put("usuario", usuario);
                    ctx.render("/templates/usuario/tela_alterar.html", map);
                }
                // ctx.render("/templates/curso/tela_adicionar.html");
            });

            //ALUNO=========================================================================================

            config.routes.get("/alunos", ctx -> {
                // crio um map <chave, valor> para que seja usado la no html
                Map<String, Object> map = new HashMap<>();
                System.out.println(map.put("vetorAluno", new AlunoDAO().listarTodos()));
                               
                // renderizo a pagina html encaminhando tb o map
                ctx.render("/templates/aluno/index.html", map);
            });

            config.routes.get("/aluno/excluir/{matricula}", ctx -> {
                new AlunoDAO().excluir(ctx.pathParam("matricula"));
                ctx.redirect("/alunos");
            });

             config.routes.get("/aluno/tela_adicionar", ctx -> {
                ctx.render("/templates/aluno/tela_adicionar.html");
            });

            config.routes.post("/aluno/tela_adicionar", ctx -> {

                String matricula = ctx.formParam("matricula");
        
                int cursoId = ctx.formParamAsClass("curso.id", Integer.class)
                         .getOrThrow(e -> new BadRequestResponse("Curso não selecionado"));
                         
                int usuarioId = ctx.formParamAsClass("usuario.id", Integer.class)
                           .getOrThrow(e -> new BadRequestResponse("Usuário inválido"));
                                               

                Aluno aluno = new Aluno();
                aluno.setMatricula(matricula);
                
                Curso curso = new Curso();
                curso.setId(cursoId);
                aluno.setCurso(curso);
            
                Usuario usuario = new Usuario();
                usuario.setId(usuarioId);
                aluno.setUsuario(usuario);

                
                if (new AlunoDAO().salvar(aluno)) {
                    ctx.redirect("/alunos");
                } else {
                    ctx.redirect("/templates/aluno/tela_adicionar.html");
                }
                // ctx.render("/templates/curso/tela_adicionar.html");
            });

            
            config.routes.get("/aluno/tela_alterar/{matricula}", ctx -> {
                String mat = ctx.pathParam("matricula");
                Aluno aluno = new AlunoDAO().buscar(mat); 

                // DEBUG: Se isso imprimir null ou vazio, o formulário vai quebrar
                System.out.println("Aluno buscado: " + aluno.getMatricula());

                Map<String, Object> map = new HashMap<>();
                map.put("aluno", aluno); 
                ctx.render("/templates/aluno/tela_alterar.html", map);
            });

            config.routes.post("/aluno/tela_alterar/{matricula}", ctx -> {

                String matricula = ctx.pathParam("matricula");
                String status = ctx.formParam("status");

                Aluno aluno = new Aluno();
                aluno.setMatricula(matricula); 
                aluno.setStatus(status);
                Map<String, Object> map = new HashMap<>();

                if (new AlunoDAO().atualizar(aluno)) {
                    ctx.redirect("/alunos");
                } else {
                    // defino um apelido para a colecao de objetos de curso vindos do banco
                    map.put("aluno", aluno);
                    ctx.render("/templates/aluno/tela_alterar.html", map);
                }
                // ctx.render("/templates/curso/tela_adicionar.html");
            });

            //TIPO REQUERIMENTO========================================================

            config.routes.get("/tipo_requerimento", ctx -> {
                // crio um map <chave, valor> para que seja usado la no html
                Map<String, Object> map = new HashMap<>();
                System.out.println(map.put("vetorRequerimento", new TipoRequerimentoDAO().listarTodos()));
                               
                // renderizo a pagina html encaminhando tb o map
                ctx.render("/templates/tipo_requerimento/index.html", map);
            });

            config.routes.get("/tipo_requerimento/excluir/{id}", ctx -> {
                new TipoRequerimentoDAO().excluir(Integer.parseInt(ctx.pathParam("id")));
                ctx.redirect("/tipo_requerimento");
            });

            config.routes.get("/tipo_requerimento/tela_adicionar", ctx -> {
                ctx.render("templates/tipo_requerimento/tela_adicionar.html");
            });

            config.routes.post("/tipo_requerimento/tela_adicionar", ctx -> {

                String descricao = ctx.formParam("descricao");

                if (descricao == null || descricao.trim().isEmpty()) {
                    ctx.status(400).result("A descrição é obrigatória");
                    return;
                }
              
                TipoRequerimento tipo = new TipoRequerimento();
                tipo.setDescricao(descricao);
            
            
                if (new TipoRequerimentoDAO().salvar(tipo)) {
                    ctx.redirect("/tipo_requerimento"); // Volta para a lista
                } else {
                    ctx.result("Erro ao salvar no banco de dados");
                }
            });

            config.routes.get("/tipo_requerimento/tela_alterar/{id}", ctx -> {

                int idBusca = Integer.parseInt(ctx.pathParam("id"));
                TipoRequerimento tr = new TipoRequerimentoDAO().buscar(idBusca);

                Map<String, Object> map = new HashMap<>();
                
                map.put("tipo", tr); 
                
                System.out.println("ID carregado no Java: " + tr.getId());

                ctx.render("/templates/tipo_requerimento/tela_alterar.html", map);
            });

            config.routes.post("/tipo_requerimento/tela_alterar", ctx -> {

                int id = Integer.parseInt(ctx.formParam("id")); 
                String descricao = ctx.formParam("descricao");

                TipoRequerimento tipo = new TipoRequerimento();
                tipo.setId(id);
                tipo.setDescricao(descricao);

                if (new TipoRequerimentoDAO().atualizar(tipo)) {
                    ctx.redirect("/tipo_requerimento");
                } else {
                    ctx.render("/templates/tipo_requerimento/tela_alterar.html", Map.of("tipo", tipo));
                }
            });



            //REQUERIMENTO=============================================================
            
            config.routes.get("/requerimento", ctx -> {
                // crio um map <chave, valor> para que seja usado la no html
                Map<String, Object> map = new HashMap<>();
                System.out.println(map.put("lista", new RequerimentoDAO().listarTodos()));
                               
                // renderizo a pagina html encaminhando tb o map
                ctx.render("/templates/requerimento/index.html", map);
            });

            config.routes.get("/requerimento/excluir/{id}", ctx -> {
                new RequerimentoDAO().excluir(Integer.parseInt(ctx.pathParam("id")));
                ctx.redirect("/requerimento");
            });

            config.routes.get("/requerimento/tela_adicionar", ctx -> {

                Map<String, Object> map = new HashMap<>();
                
                map.put("tipos", new TipoRequerimentoDAO().listarTodos());
                
                ctx.render("/templates/requerimento/tela_adicionar.html", map);
                
            });

            config.routes.post("/requerimento/adicionar", ctx -> {
                String matricula = ctx.formParam("matricula");
                int tipoId = Integer.parseInt(ctx.formParam("tipo_id"));
                String observacao = ctx.formParam("observacao");

                int idGerado = new RequerimentoDAO().abrirRequerimento(matricula, tipoId, observacao);

                if (idGerado > 0) {
                    UploadedFile file = ctx.uploadedFile("anexo");

                    // Se tiver arquivo, salva o anexo
                    if (file != null && file.size() > 0) {
                        Anexo anexo = new Anexo();
                        anexo.setDescricao(file.filename());
                        anexo.setArquivo(file.content().readAllBytes());
                        
                        Requerimento reqPai = new Requerimento();
                        reqPai.setId(idGerado);
                        anexo.setRequerimento(reqPai);

                        new AnexoDAO().salvar(anexo);
                    }

                    ctx.redirect("/requerimento");
                
                } else {
                    ctx.result("Erro ao abrir requerimento no banco de dados.");
                }
            });

            config.routes.get("/requerimento/alterar/{id}", ctx -> {
                int id = Integer.parseInt(ctx.pathParam("id"));
                Requerimento req = new RequerimentoDAO().buscarPorId(id);

                if (req != null) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("requerimento", req);
                    map.put("tipos", new TipoRequerimentoDAO().listarTodos());
                    ctx.render("/templates/requerimento/tela_alterar.html", map);
                } else {
                    ctx.result("Requerimento não encontrado.");
                }
            });

            config.routes.post("/requerimento/alterar/{id}", ctx -> {
                int id = Integer.parseInt(ctx.pathParam("id"));
                String novoStatus = ctx.formParam("status");

                Requerimento req = new Requerimento();
                req.setId(id);
                req.setStatus(novoStatus);
                req.setObservacao(ctx.formParam("observacao")); 
                
                // 1. Atualiza o Requerimento (Status e Data Encerramento)
                if (new RequerimentoDAO().atualizar(req)) {
                    
                    // 2. Opcional: Se o usuário enviou um novo arquivo na correção
                    UploadedFile file = ctx.uploadedFile("anexo");
                    if (file != null && file.size() > 0) {
                        Anexo anexo = new Anexo();
                        anexo.setDescricao(file.filename());
                        anexo.setArquivo(file.content().readAllBytes());
                        
                        Requerimento reqPai = new Requerimento();
                        reqPai.setId(id);
                        anexo.setRequerimento(reqPai);

                        // Chama o método que deleta o antigo e insere o novo
                        new AnexoDAO().atualizar(anexo);
                    }

                    ctx.redirect("/requerimento");
                } else {
                    ctx.result("Erro ao atualizar status do requerimento.");
                }
            });

          

            

             


            



           


            // defino que minha aplicacao rodara na porta 7070
        }).start(7070);
    }
}