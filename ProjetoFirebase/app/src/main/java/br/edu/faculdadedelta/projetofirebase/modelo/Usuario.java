package br.edu.faculdadedelta.projetofirebase.modelo;

import java.io.Serializable;

public class Usuario implements Serializable {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String uid;
    private int qtdAcertos;

    public Usuario() {
    }

    public Usuario(Long id, String uid, String nome, String email, String senha, int qtdAcertos) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.uid = uid;
        this.qtdAcertos = qtdAcertos;
    }

    public String getUid() {
        return uid;
    }

    public int getQtdAcertos() {
        return qtdAcertos;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setQtdAcertos(int qtdAcertos) {
        this.qtdAcertos = qtdAcertos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;

        Usuario usuario = (Usuario) o;

        return getId().equals(usuario.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
