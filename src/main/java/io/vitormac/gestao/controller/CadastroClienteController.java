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
            new Alert(Alert.AlertType.INFORMATION, "Cliente cadastrado com sucesso!").showAndWait();
        } else if (!cliente.isDocumentoValido()) {
            StringBuilder builder = new StringBuilder("Documento informado é inválido!");
            builder.append('\n').append("Formato esperado para pessoa ")
                    .append(cliente.getTipoPessoa().name()).append(':').append('\n')
                    .append(cliente.getFormatoDocumento()).append(" (apenas números)");
            
            new Alert(Alert.AlertType.WARNING, builder.toString()).showAndWait();
        } else {
            new Alert(Alert.AlertType.WARNING, "Cliente já cadastrado!").showAndWait();
        }
    }

    private boolean hasCliente(ClientePessoa cliente) {
        return !this.manager.createNamedQuery("Cliente.existe", ClientePessoa.class)
                .setParameter("nome", cliente.getNome())
                .setParameter("documento", cliente.getDocumento())
                .getResultList().isEmpty();
    }

}
