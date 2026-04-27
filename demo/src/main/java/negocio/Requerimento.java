package negocio;

import java.sql.Timestamp;

public class Requerimento {
    private int id;
    private Timestamp data_hora_abertura;
    private Timestamp data_hora_encerramento;
    private String observacao;
    private String status;
    private TipoRequerimento tipo;
    private Aluno aluno;

    public Requerimento(){}

    public Requerimento(Timestamp data_hora_abertura, Timestamp data_hora_encerramento, String observacao, String status, TipoRequerimento tipo, Aluno aluno){
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

    public Timestamp getDataHoraAbertura(){
        return data_hora_abertura;
    }
    public void setDataHoraAbertura(Timestamp data_hora_abertura){
        this.data_hora_abertura = data_hora_abertura;
    }

    public Timestamp getDataHoraEncerramento(){
        return data_hora_encerramento;
    }
    public void setDataHoraEncerramento(Timestamp data_hora_encerramento){
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
