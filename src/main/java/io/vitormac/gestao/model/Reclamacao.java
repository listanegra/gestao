package io.vitormac.gestao.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

/**
 *
 * @author vitor
 */
@Entity
@NamedQuery(name = "Reclamacao.listarReclamacoes", query = "SELECT r FROM Reclamacao r")
public class Reclamacao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int protocolo;

    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private ClientePessoa cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    private Prioridade prioridade;
    private Status status;

    public Reclamacao() {

    }

    public Reclamacao(String descricao, ClientePessoa cliente, Produto produto, Prioridade prioridade) {
        this.descricao = descricao;
        this.cliente = cliente;
        this.produto = produto;
        this.prioridade = prioridade;
        this.status = Status.PENDENTE;
    }

    public int getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(int protocolo) {
        this.protocolo = protocolo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public ClientePessoa getCliente() {
        return cliente;
    }

    public void setCliente(ClientePessoa cliente) {
        this.cliente = cliente;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Prioridade {

        BAIXA, MEDIA, ALTA, URGENTE;

    }

    public enum Status {

        ENCERRADO, RESOLVIDO, PENDENTE;

    }

}
