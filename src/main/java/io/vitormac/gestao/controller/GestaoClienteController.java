package io.vitormac.gestao.controller;

import io.vitormac.gestao.controller.model.GestaoControllerBase;
import io.vitormac.gestao.model.ClientePessoa;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;

/**
 *
 * @author vitor
 */
public class GestaoClienteController extends GestaoControllerBase {

    @FXML
    private TableColumn nomeCol;

    @FXML
    private TableColumn documentoCol;

    @FXML
    private TableColumn tipoPessoaCol;

    @FXML
    private TableView<ClientePessoa> tableClientes;

    @FXML
    private void alterarNome(CellEditEvent<ClientePessoa, String> event) {
        ClientePessoa cliente = event.getRowValue();
        if (!event.getNewValue().equals(event.getOldValue())) {
            cliente.setNome(event.getNewValue());
            this.incluirItem(cliente);
        }
    }

    @FXML
    private void alterarDocumento(CellEditEvent<ClientePessoa, String> event) {
        ClientePessoa cliente = event.getRowValue();
        if (!event.getNewValue().equals(event.getOldValue())) {
            cliente.setDocumento(event.getNewValue());
            if (cliente.isDocumentoValido()) {
                this.incluirItem(cliente);
            } else {
                cliente.setDocumento(event.getOldValue());

                Alert alert = new Alert(Alert.AlertType.WARNING, cliente.getMensagemAlerta());
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        }
    }

    @FXML
    @Override
    public void incluir(ActionEvent event) throws IOException {
        this.dialogInclusao("cadastro_cliente", "Novo cliente", this.tableClientes.getItems())
                .ifPresent(this.tableClientes.getItems()::add);
    }

    @FXML
    @Override
    public void excluir(ActionEvent event) {
        ClientePessoa cliente = this.tableClientes.getSelectionModel().getSelectedItem();
        this.alertaExclusao(cliente).ifPresent(this.tableClientes.getItems()::remove);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.nomeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        this.nomeCol.setCellValueFactory(new PropertyValueFactory("nome"));
        this.documentoCol.setCellFactory(TextFieldTableCell.forTableColumn(new DocumentoConverter()));
        this.documentoCol.setCellValueFactory(new PropertyValueFactory("documento"));
        this.tipoPessoaCol.setCellValueFactory(new PropertyValueFactory("tipoPessoa"));

        List<ClientePessoa> clientes = this.consultar("Cliente.listarClientes", ClientePessoa.class);
        this.tableClientes.setItems(FXCollections.observableArrayList(clientes));
    }

    private class DocumentoConverter extends StringConverter<String> {

        @Override
        public String toString(String object) {
            return object;
        }

        @Override
        public String fromString(String string) {
            return string.replaceAll("[^0-9]", "");
        }

    }

}
