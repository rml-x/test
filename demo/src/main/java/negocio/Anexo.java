package negocio;

public class Anexo {
    private int id;
    private String descricao;
    private byte[] arquivo;
    private Requerimento requerimento;

    public Anexo(){}

    public Anexo(int id, String descricao, byte[] arquivo, Requerimento requerimento){
        this.id = id;
        this.descricao = descricao;
        this.arquivo = arquivo;
        this.requerimento = requerimento;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getDescricao(){
        return descricao;
    }
    public void setDescricao(String descricao){
        this.descricao = descricao;

    }

    public byte[] getArquivo(){
        return arquivo;
    }
    public void setArquivo(byte[] arquivo){
        this.arquivo = arquivo;

    }

    public Requerimento getRequerimento(){
        return requerimento;
    }
    public void setRequerimento(Requerimento requerimento){
        this.requerimento = requerimento;
    }
}
