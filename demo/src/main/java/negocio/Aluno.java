package negocio;

public class Aluno {
    private String matricula; // Primary Key
    private Usuario usuario;  // Mapeamento da FK usuario_id
    private Curso curso;      // Você pode criar a classe Curso depois e trocar aqui
    private String status;

    public Aluno(){}

    public Aluno(String matricula, Usuario usuario, Curso curso, String status){
        this.matricula = matricula;
        this.usuario = usuario;
        this.curso = curso;
        this.status = status;
    }

        public String getMatricula(){
            return matricula; 
        }
        public void setMatricula(String matricula){
            this.matricula = matricula; 
        }

        public Usuario getUsuario(){
            return usuario; 
        }
        public void setUsuario(Usuario usuario){
            this.usuario = usuario; 
        }

        public Curso getCurso(){
            return curso; 
        }
        public void setCurso(Curso curso){
            this.curso = curso; 
        }

        public String getStatus(){
            return status; 
        }
        public void setStatus(String status){
            this.status = status; 
        }
    }

