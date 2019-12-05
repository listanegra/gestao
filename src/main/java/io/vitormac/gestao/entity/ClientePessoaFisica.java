package io.vitormac.gestao.entity;

import javax.persistence.Entity;

/**
 *
 * @author vitor
 */
@Entity
public class ClientePessoaFisica extends ClientePessoa {

    public ClientePessoaFisica() {
        super();
    }

    public ClientePessoaFisica(String nome, String documento) {
        super(nome, documento, TipoPessoa.FISICA);
    }

    @Override
    public boolean isDocumentoValido() {
        return this.getDocumento().length() == 11 && this.getDocumento().matches("^[0-9]*$");
    }

    @Override
    public String getFormatoDocumento() {
        return "###.###.###-##";
    }

    @Override
    protected String formatarDocumento() {
        return this.getDocumento().replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

}
