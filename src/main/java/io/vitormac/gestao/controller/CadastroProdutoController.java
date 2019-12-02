package io.vitormac.gestao.controller;

import io.vitormac.gestao.controller.model.DialogCadastroBase;
import io.vitormac.gestao.model.Produto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author vitor
 */
public final class CadastroProdutoController extends DialogCadastroBase<Produto> {

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtMarca;

    @FXML
    private TextArea txtDescricao;

    @FXML
    @Override
    protected void action(ActionEvent event) {
        this.doAction(new Produto(this.txtNome.getText(), this.txtMarca.getText(), this.txtDescricao.getText()), event);
    }

    @Override
    protected boolean validate(Produto produto) {
        if (produto.getNome().isEmpty()) {
            this.alert("Informe o nome do produto!");
            return false;
        }
        
        if (produto.getMarca().isEmpty()) {
            this.alert("Informe a marca do produto!");
            return false;
        }
        
        if (this.exists(e -> e.getNome().equals(produto.getNome()) && e.getMarca().equals(produto.getMarca()))) {
            this.alert("Produto já cadastrado!");
            return false;
        }
        
        return true;
    }

}
