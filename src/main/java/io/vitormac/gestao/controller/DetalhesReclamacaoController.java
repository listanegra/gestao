package io.vitormac.gestao.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class DetalhesReclamacaoController {

    @FXML
    private TextArea txtDescricao;

    protected void setDescricao(String descricao) {
        this.txtDescricao.setText(descricao);
    }

}
