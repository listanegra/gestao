package io.vitormac.gestao.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author vitor
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQuery(name = "Cliente.listarClientes", query = "SELECT c FROM ClientePessoa c")
public abstract class ClientePessoa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome, documento;

    @Column(name = "tipo_pessoa")
    @Enumerated(EnumType.ORDINAL)
    private TipoPessoa tipoPessoa;

    @ElementCollection
    @OneToMany(mappedBy = "cliente")
    private List<Reclamacao> reclamacoes = new ArrayList<>();

    protected ClientePessoa() {
    }

    protected ClientePessoa(String nome, String documento, TipoPessoa tipoPessoa) {
        this.nome = nome;
        this.documento = documento;
        this.tipoPessoa = tipoPessoa;
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

    public TipoPessoa getTipoPessoa() {
        return this.tipoPessoa;
    }

    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public String getMensagemAlerta() {
        StringBuilder builder = new StringBuilder("Documento informado é inválido!");
        builder.append('\n').append("Formato esperado para pessoa ")
                .append(this.getTipoPessoa().name()).append(':').append('\n')
                .append(this.getFormatoDocumento()).append(" (apenas números)");
        return builder.toString();
    }

    public abstract boolean isDocumentoValido();

    public abstract String getFormatoDocumento();

    protected abstract String formatarDocumento();

    @Override
    public final String toString() {
        return String.format("%s - Documento: %s", this.getNome(), this.formatarDocumento());
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        
        if (obj instanceof ClientePessoa) {
            ClientePessoa pessoa = (ClientePessoa) obj;
            return pessoa.getNome().equals(this.nome) && pessoa.getDocumento().equals(this.documento) && pessoa.getTipoPessoa().equals(this.tipoPessoa);
        }
        
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.id;
        return hash;
    }

    public List<Reclamacao> getReclamacoes() {
        return reclamacoes;
    }

    public void setReclamacoes(List<Reclamacao> reclamacoes) {
        this.reclamacoes = reclamacoes;
    }

    public enum TipoPessoa {

        FISICA, JURIDICA

    }

}
