package io.vitormac.gestao.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;

/**
 *
 * @author vitor
 */
@Entity
@NamedQuery(name = "Atendente.listarAtendentes", query = "SELECT a FROM Atendente a")
public class Atendente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome, token;

    @ElementCollection
    @OneToMany(mappedBy = "atendente")
    private List<Reclamacao> reclamacoes = new ArrayList<>();

    public Atendente() {
    }

    public Atendente(String nome, String senha) {
        this.nome = nome;
        this.token = UUID.nameUUIDFromBytes(this.nome.concat(senha).getBytes()).toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void set(Atendente atendente) {
        this.id = atendente.id;
        this.nome = atendente.nome;
        this.token = atendente.token;
        this.reclamacoes.addAll(atendente.reclamacoes);
    }

    @Override
    public String toString() {
        return String.format("%s - %s", this.nome, this.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        
        if (obj instanceof Atendente) {
            Atendente atendente = (Atendente) obj;
            return atendente.getNome().equals(this.nome) && atendente.getToken().equals(this.token);
        }
        
        return false;
    }

    public List<Reclamacao> getReclamacoes() {
        return reclamacoes;
    }

    public void setReclamacoes(List<Reclamacao> reclamacoes) {
        this.reclamacoes = reclamacoes;
    }

}
