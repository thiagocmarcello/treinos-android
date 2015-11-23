package br.com.meutreino.app.entidade;

import java.io.Serializable;

/**
 * Created by thiago on 22/06/15.
 */
public class Treino implements Serializable {

    private int repeticao;
    private String nome;
    private int sequencia;
    private String descricao;


    public int getRepeticao() {
        return repeticao;
    }

    public void setRepeticao(int repeticao) {
        this.repeticao = repeticao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getSequencia() {
        return sequencia;
    }

    public void setSequencia(int sequencia) {
        this.sequencia = sequencia;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
