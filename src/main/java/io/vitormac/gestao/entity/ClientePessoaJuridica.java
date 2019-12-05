package io.vitormac.gestao.entity;

import javax.persistence.Entity;

/**
 *
 * @author vitor
 */
@Entity
public class ClientePessoaJuridica extends ClientePessoa {

    public ClientePessoaJuridica() {
        super();
    }

    public ClientePessoaJuridica(String nome, String documento) {
        super(nome, documento, TipoPessoa.JURIDICA);
    }

    @Override
    public boolean isDocumentoValido() {
        return this.getDocumento().length() == 14 && this.getDocumento().matches("^[0-9]*$");
    }

    @Override
    public String getFormatoDocumento() {
        return "##.###.###/####-##";
    }

    @Override
    protected String formatarDocumento() {
        return this.getDocumento().replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
    }

}
