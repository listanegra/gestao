package io.vitormac.gestao.model;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

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

}
