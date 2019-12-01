package io.vitormac.gestao.controller;

import io.vitormac.gestao.model.ClientePessoa;
import io.vitormac.gestao.utils.DatabaseManager;
import io.vitormac.gestao.utils.SceneUtils;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author vitor
 */
public class GestaoClienteController implements Initializable {

    private final EntityManager manager = DatabaseManager.createManager();

    @FXML
    private TableColumn nomeCol;

    @FXML
    private TableColumn documentoCol;

    @FXML
    private TableColumn tipoPessoaCol;

    @FXML
    private TableView<ClientePessoa> tableClientes;

    private boolean pendente = false;
    private final List<ClientePessoa> removidos = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.nomeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        this.nomeCol.setCellValueFactory(new PropertyValueFactory("nome"));
        this.documentoCol.setCellFactory(TextFieldTableCell.forTableColumn(new DocumentoConverter()));
        this.documentoCol.setCellValueFactory(new PropertyValueFactory("documento"));
        this.tipoPessoaCol.setCellValueFactory(new PropertyValueFactory("tipoPessoa"));
        List<ClientePessoa> clientes = this.manager.createNamedQuery("Cliente.listarClientes", ClientePessoa.class).getResultList();
        this.tableClientes.setItems(FXCollections.observableArrayList(clientes));
    }

    @FXML
    private void incluirCliente(ActionEvent event) throws IOException {
        FXMLLoader loader = SceneUtils.getLoader("cadastro_cliente");
        Stage stage = SceneUtils.createDialog(SceneUtils.loadScene(loader), "Novo cliente");
        CadastroClienteController controller = loader.getController();
        controller.carregarClientes(this.tableClientes.getItems());

        stage.setOnHiding(e -> controller.getCliente().ifPresent(cliente -> {
            this.tableClientes.getItems().add(cliente);
            this.pendente = true;
        }));
        stage.showAndWait();
    }

    @FXML
    private void excluirCliente(ActionEvent event) {
        ClientePessoa cliente = this.tableClientes.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, String.format("Deseja excluir cliente %s?", cliente.getNome()));
        alert.getButtonTypes().setAll(ButtonType.NO, ButtonType.YES);
        alert.setHeaderText(null);

        Optional<ButtonType> option = alert.showAndWait();
        if (option.get().equals(ButtonType.YES)) {
            this.tableClientes.getItems().remove(cliente);
            this.removidos.add(cliente);
            this.pendente = true;
        }
    }

    @FXML
    private void alterarNome(CellEditEvent<ClientePessoa, String> event) {
        ClientePessoa cliente = event.getRowValue();
        if (!event.getNewValue().equals(event.getOldValue())) {
            cliente.setNome(event.getNewValue());
            this.pendente = true;
        }
    }

    @FXML
    private void alterarDocumento(CellEditEvent<ClientePessoa, String> event) {
        ClientePessoa cliente = event.getRowValue();
        if (!event.getNewValue().equals(event.getOldValue())) {
            cliente.setDocumento(event.getNewValue());
            if (cliente.isDocumentoValido()) {
                this.pendente = true;
            } else {
                cliente.setDocumento(event.getOldValue());

                Alert alert = new Alert(Alert.AlertType.WARNING, cliente.getMensagemAlerta());
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        }
    }

    public void confirmaAlteracao(WindowEvent event) {
        if (this.pendente) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Deseja salvar alterações?");
            alert.getButtonTypes().setAll(ButtonType.CANCEL, ButtonType.NO, ButtonType.YES);
            alert.setHeaderText(null);

            Optional<ButtonType> option = alert.showAndWait();
            if (option.get().equals(ButtonType.YES)) {
                this.tableClientes.getItems().forEach(this::persisteCliente);
                this.removidos.forEach(this::removeCliente);
            } else if (option.get().equals(ButtonType.CANCEL)) {
                event.consume();
            }
        }
    }

    private void persisteCliente(ClientePessoa cliente) {
        EntityTransaction transaction = this.manager.getTransaction();

        transaction.begin();
        this.manager.persist(cliente);
        transaction.commit();
    }

    private void removeCliente(ClientePessoa cliente) {
        EntityTransaction transaction = this.manager.getTransaction();

        transaction.begin();
        this.manager.remove(cliente);
        transaction.commit();
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
