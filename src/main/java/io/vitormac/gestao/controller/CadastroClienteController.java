package io.vitormac.gestao.controller;

import io.vitormac.gestao.DatabaseManager;
import io.vitormac.gestao.model.Cliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
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
    public void incluirCliente(ActionEvent event) {
        Cliente cliente = new Cliente(this.txtNome.getText(), this.txtDocumento.getText());
        if (!this.hasCliente(cliente)) {
            EntityTransaction transaction = manager.getTransaction();

            transaction.begin();
            manager.persist(cliente);
            transaction.commit();

            Node source = (Node) event.getSource();
            ((Stage) source.getScene().getWindow()).close();
            new Alert(Alert.AlertType.INFORMATION, "Cliente cadastrado com sucesso!").showAndWait();
        } else {
            new Alert(Alert.AlertType.WARNING, "Cliente já cadastrado!").showAndWait();
        }
    }

    private boolean hasCliente(Cliente cliente) {
        return !this.manager.createNamedQuery("Cliente.existe", Cliente.class)
                .setParameter("nome", cliente.getNome())
                .setParameter("documento", cliente.getDocumento())
                .getResultList().isEmpty();
    }

}
