package io.vitormac.gestao.controller;

import io.vitormac.gestao.controller.model.DialogCadastroBase;
import io.vitormac.gestao.entity.Atendente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.Optional;

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

        Optional<Atendente> optional = this.getItems().stream()
                .filter(e -> e.equals(atendente)).findAny();
        optional.ifPresent(atendente::set);
        return true;
    }

}
