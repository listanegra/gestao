package io.vitormac.gestao.controller;

import io.vitormac.gestao.utils.DatabaseManager;
import io.vitormac.gestao.model.ClientePessoa;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javax.persistence.EntityManager;

/**
 *
 * @author vitor
 */
public class NovoProtocoloController implements Initializable {

    private final EntityManager manager = DatabaseManager.createManager();
    
    @FXML
    private ComboBox cbClientes;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<ClientePessoa> clientes = manager.createNamedQuery("Cliente.listarClientes", ClientePessoa.class).getResultList();
        this.cbClientes.setItems(FXCollections.observableArrayList(clientes));
    }

    @FXML
    public void incluirProtocolo(ActionEvent event) {

        Node source = (Node) event.getSource();
        ((Stage) source.getScene().getWindow()).close();
    }

}
