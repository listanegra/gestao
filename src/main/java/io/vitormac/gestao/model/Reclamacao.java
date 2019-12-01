package io.vitormac.gestao.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

/**
 *
 * @author vitor
 */
@Entity
public class Reclamacao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int protocolo;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    private ClientePessoa cliente;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    private Produto produto;

    private Prioridade prioridade;
    private Status status;

    public int getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(int protocolo) {
        this.protocolo = protocolo;
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
