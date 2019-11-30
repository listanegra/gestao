package io.vitormac.gestao.model;

import java.io.Serializable;
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
@NamedQuery(name = "Cliente.listarClientes", query = "SELECT c FROM Cliente c")
@NamedQuery(name = "Cliente.existe", query = "SELECT c FROM Cliente c WHERE c.nome = :nome AND c.documento = :documento")
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome, documento;

    public Cliente() {
    }

    public Cliente(String nome, String documento) {
        this.nome = nome;
        this.documento = documento;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDocumento() {
        return this.documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    @Override
    public String toString() {
        return String.format("%s - Documento: %s", this.nome, this.documento);
    }

}
