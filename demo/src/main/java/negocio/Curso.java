package negocio;

public class Curso {
    private int id;
    private String nome;
    private String site;
    private String turno;
    private int duracao;
    private boolean ativo;

    public Curso(){}

    public Curso(String nome, String site, String turno, int duracao, boolean ativo){
    this.nome = nome;
    this.site = site;
    this.turno = turno;
    this.duracao = duracao;
    this.ativo = ativo;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getNome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }

    public String getSite(){
        return site;
    }
    public void setSite(String site){
        this.site = site;
    }

    public String getTurno(){
        return turno;
    }
    public void setTurno(String turno){
        this.turno = turno;
    }

    public int getDuracao() {
        return this.duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public boolean setAtivo(){
        return ativo;
    }
    public void getAtivo(boolean ativo){
        this.ativo = ativo;
    }


}
