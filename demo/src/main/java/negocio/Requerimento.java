package negocio;

import java.time.LocalDateTime;

public class Requerimento {
    private int id;
    private LocalDateTime data_hora_abertura;
    private LocalDateTime data_hora_encerramento;
    private String observacao;
    private String status;
    private TipoRequerimento tipo;
    private Aluno aluno;

    public Requerimento(){}

    public Requerimento(LocalDateTime data_hora_abertura, LocalDateTime data_hora_encerramento, String observacao, String status, TipoRequerimento tipo, Aluno aluno){
        this.data_hora_abertura = data_hora_abertura;
        this.data_hora_encerramento = data_hora_encerramento;
        this.observacao = observacao;
        this.status = status;
        this.tipo = tipo;
        this.aluno = aluno;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public LocalDateTime getDataHoraAbertura(){
        return data_hora_abertura;
    }
    public void setDataHoraAbertura(LocalDateTime data_hora_abertura){
        this.data_hora_abertura = data_hora_abertura;
    }

    public LocalDateTime getDataHoraEncerramento(){
        return data_hora_encerramento;
    }
    public void setDataHoraEncerramento(LocalDateTime data_hora_encerramento){
        this.data_hora_encerramento = data_hora_encerramento;
    }

    public String getObservacao(){
        return observacao;
    }
    public void setObservacao(String observacao){
        this.observacao = observacao;
    }

    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status = status;
    }

    public TipoRequerimento getTipo(){
        return tipo;
    }
    public void setTipo(TipoRequerimento tipo){
        this.tipo = tipo;
    }

    public Aluno getAluno(){
        return aluno;
    }
    public void setAluno(Aluno aluno){
        this.aluno = aluno;
    }


}
