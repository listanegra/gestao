package io.vitormac.gestao.controller;

import io.vitormac.gestao.controller.model.DialogCadastroBase;
import io.vitormac.gestao.model.ClientePessoa;
import io.vitormac.gestao.model.ClientePessoaFisica;
import io.vitormac.gestao.model.ClientePessoaJuridica;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 *
 * @author vitor
 */
public final class CadastroClienteController extends DialogCadastroBase<ClientePessoa> {

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtDocumento;

    @FXML
    private RadioButton pessoaFisica, pessoaJuridica;

    @FXML
    @Override
    protected void action(ActionEvent event) {
        if (pessoaFisica.isSelected()) {
            this.doAction(new ClientePessoaFisica(this.txtNome.getText(), this.txtDocumento.getText()), event);
        } else if (pessoaJuridica.isSelected()) {
            this.doAction(new ClientePessoaJuridica(this.txtNome.getText(), this.txtDocumento.getText()), event);
        }
    }

    @Override
    protected boolean validate(ClientePessoa cliente) {
        if (cliente.getNome().isEmpty()) {
            this.alert("Informe o nome do cliente!");
            return false;
        }
        
        if (!cliente.isDocumentoValido()) {
            this.alert(cliente.getMensagemAlerta());
            return false;
        }

        if (this.contains(cliente)) {
            this.alert("Cliente já cadastrado!");
            return false;
        }
        
        return true;
    }

}
