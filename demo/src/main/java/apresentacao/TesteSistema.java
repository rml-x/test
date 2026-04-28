package apresentacao;

import java.sql.SQLException;
import java.util.List;
import negocio.Anexo;
import negocio.Requerimento;
import persistencia.AnexoDAO;
import persistencia.RequerimentoDAO;

public class TesteSistema {
    public static void main(String[] args) {
        // Instanciando os DAOs
        RequerimentoDAO reqDAO = new RequerimentoDAO();
        AnexoDAO anexoDAO = new AnexoDAO();

        try {
            System.out.println("========== TESTE DE INTEGRAÇÃO - SISTEMA REQUERIMENTO ==========");

            // 1. TESTE: Abrir Requerimento (INSERT)
            // Certifique-se que a matrícula '2023100001' e o tipoId '1' existem no banco
            System.out.println("\n[1] Abrindo novo requerimento...");
            boolean sucessoAbertura = reqDAO.abrirRequerimento("2023100001", 1, "Solicito revisão da nota da prova final.");
            System.out.println("Resultado: " + (sucessoAbertura ? "Sucesso!" : "Falha!"));

            // 2. TESTE: Listar Todos (SELECT Geral com Joins)
            // Como não há método mapear, aqui testamos se o preenchimento manual no DAO deu certo
            System.out.println("\n[2] Listando todos os requerimentos (Visão Admin):");
            List<Requerimento> todos = reqDAO.listarTodos();
            
            if (todos.isEmpty()) {
                System.out.println("Nenhum requerimento encontrado no banco.");
            } else {
                for (Requerimento r : todos) {
                    System.out.println("--------------------------------------------------");
                    System.out.println("ID REQ: " + r.getId());
                    System.out.println("STATUS: " + r.getStatus());
                    System.out.println("TIPO:   " + r.getTipo().getDescricao()); // Testa o objeto TipoRequerimento
                    System.out.println("ALUNO:  " + r.getAluno().getUsuario().getNome()); // Testa Requerimento -> Aluno -> Usuario
                    System.out.println("CURSO:  " + r.getAluno().getCurso().getNome()); // Testa Requerimento -> Aluno -> Curso
                    System.out.println("DATA:   " + r.getDataHoraAbertura());
                }
            }

            // 3. TESTE: Salvar Anexo (INSERT BYTEA)
            if (!todos.isEmpty()) {
                System.out.println("\n[3] Testando anexo no primeiro requerimento da lista...");
                Requerimento ref = todos.get(0);
                
                Anexo doc = new Anexo();
                doc.setDescricao("Comprovante_Inscricao.pdf");
                doc.setArquivo("CONTEUDO_BINARIO_TESTE".getBytes()); // Simulando um arquivo
                doc.setRequerimento(ref);
                
                if (anexoDAO.salvar(doc)) {
                    System.out.println("Anexo salvo com ID: " + doc.getId());
                }
            }

            // 4. TESTE: Atualizar Status (UPDATE)
            if (!todos.isEmpty()) {
                System.out.println("\n[4] Atualizando status do último requerimento...");
                Requerimento ultimo = todos.get(todos.size() - 1);
                ultimo.setStatus("DEFERIDO");
                
                if (reqDAO.atualizar(ultimo)) {
                    System.out.println("ID " + ultimo.getId() + " atualizado para DEFERIDO.");
                }
            }

            System.out.println("\n================ TESTES FINALIZADOS ================");

        } catch (SQLException e) {
            System.err.println("\n!!! ERRO DE BANCO DE DADOS !!!");
            System.err.println("Mensagem: " + e.getMessage());
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("\n!!! ERRO DE CODIFICAÇÃO (NullPointer) !!!");
            System.err.println("Provavelmente você esqueceu de dar 'new' em algum objeto dentro do DAO.");
            e.printStackTrace();
        }
    }
}