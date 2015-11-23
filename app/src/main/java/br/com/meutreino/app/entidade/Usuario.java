package br.com.meutreino.app.entidade;

import java.io.Serializable;

/**
 * Created by thiago on 05/04/15.
 */
public class Usuario implements Serializable {

    private Integer id;
    private String nome;
    private String email;

    public Usuario() {
    }

    public Usuario(Integer id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usuario usuario = (Usuario) o;

        if (email != null ? !email.equals(usuario.email) : usuario.email != null) return false;
        if (id != null ? !id.equals(usuario.id) : usuario.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "email='" + email + '\'' +
                ", id=" + id +
                '}';
    }
}
