package negocio;

public class Aluno {
    private String matricula; 
    private Usuario usuario; 
    private Curso curso;     
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

