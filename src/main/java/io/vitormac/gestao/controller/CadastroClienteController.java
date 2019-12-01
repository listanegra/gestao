package io.vitormac.gestao.controller;

import io.vitormac.gestao.utils.DatabaseManager;
import io.vitormac.gestao.model.ClientePessoa;
import io.vitormac.gestao.model.ClientePessoaFisica;
import io.vitormac.gestao.model.ClientePessoaJuridica;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author vitor
 */
public class CadastroClienteController {

    private final EntityManager manager = DatabaseManager.createManager();

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtDocumento;

    @FXML
    private RadioButton pessoaFisica, pessoaJuridica;

    @FXML
    public void incluirCliente(ActionEvent event) {
        if (pessoaFisica.isSelected()) {
            this.cadastrar(new ClientePessoaFisica(this.txtNome.getText(), this.txtDocumento.getText()), event);
        } else if (pessoaJuridica.isSelected()) {
            this.cadastrar(new ClientePessoaJuridica(this.txtNome.getText(), this.txtDocumento.getText()), event);
        }
    }

    private void cadastrar(ClientePessoa cliente, ActionEvent event) {
        if (!this.hasCliente(cliente) && cliente.isDocumentoValido()) {
            EntityTransaction transaction = manager.getTransaction();

            transaction.begin();
            manager.persist(cliente);
            transaction.commit();

            Node source = (Node) event.getSource();
            ((Stage) source.getScene().getWindow()).close();
            this.criarAlerta(Alert.AlertType.INFORMATION, "Cliente cadastrado com sucesso!").showAndWait();
        } else if (!cliente.isDocumentoValido()) {
            this.criarAlerta(Alert.AlertType.WARNING, cliente.getMensagemAlerta()).showAndWait();
        } else {
            this.criarAlerta(Alert.AlertType.WARNING, "Cliente já cadastrado!").showAndWait();
        }
    }

    private boolean hasCliente(ClientePessoa cliente) {
        return !this.manager.createNamedQuery("Cliente.existe", ClientePessoa.class)
                .setParameter("nome", cliente.getNome())
                .setParameter("documento", cliente.getDocumento())
                .getResultList().isEmpty();
    }

    private Alert criarAlerta(Alert.AlertType type, String text) {
        Alert alert = new Alert(type, text);
        alert.setHeaderText(null);
        return alert;
    }

}
