package negocio;

import java.time.LocalDate;

public class Usuario {
    private int id;
    private String nome;
    private String email;
    private String cpf;
    private LocalDate data_nascimento;
    private String cep;
    private String rua;
    private String complemento;
    private String nro;
    private String senha;

    public Usuario(){}

    public Usuario(String nome, String email, String cpf, LocalDate data_nascimento, 
        String cep, String rua, String complemento, String nro, String senha){

        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.data_nascimento = data_nascimento;
        this.cep = cep;
        this.rua = rua;
        this.complemento = complemento;
        this.nro = nro;
        this.senha = senha;
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

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public String getCpf(){
        return cpf;
    }
    public void setCpf(String cpf){
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento(){
        return data_nascimento;
    }
    public void setDataNascimento(LocalDate data_nascimento){
        this.data_nascimento = data_nascimento;
    }

    public String getCep(){
        return cep;
    }
    public void setCep(String cep){
        this.cep = cep;
    }

    public String getRua(){
        return rua;
    }
    public void setRua(String rua){
        this.rua = rua;
    }

    public String getComplemento(){
        return complemento;
    }
    public void setComplemento(String complemento){
        this.complemento = complemento;
    }

    public String getNro(){
        return nro;
    }
    public void setNro(String nro){
        this.nro = nro;
    }

    public String getSenha(){
        return senha;
    }
    public void setSenha(String senha){
        this.senha = senha;
    }




}
