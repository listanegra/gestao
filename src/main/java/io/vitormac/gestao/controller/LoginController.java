package io.vitormac.gestao.controller;

import io.vitormac.gestao.controller.model.DialogCadastroBase;
import io.vitormac.gestao.model.Atendente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author vitor
 */
public class LoginController extends DialogCadastroBase<Atendente> {
    
    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtSenha;
    
    @FXML
    @Override
    protected void action(ActionEvent event) {
        this.doAction(new Atendente(this.txtUsuario.getText(), this.txtSenha.getText()), event);
    }

    @Override
    protected boolean validate(Atendente atendente) {
        if (atendente.getNome().isEmpty()) {
            this.alert("Informe o usuário");
            return false;
        }
        
        if (!this.contains(atendente)) {
            this.alert("Usuário/Senha incorretos!");
            return false;
        }
        
        return true;
    }

}
