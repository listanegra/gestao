package io.vitormac.gestao.controller;

import io.vitormac.gestao.model.ClientePessoa;
import io.vitormac.gestao.model.ClientePessoaFisica;
import io.vitormac.gestao.model.ClientePessoaJuridica;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author vitor
 */
public class CadastroClienteController {

    private ClientePessoa cliente;
    private final List<ClientePessoa> clientes = new ArrayList<>();

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtDocumento;

    @FXML
    private RadioButton pessoaFisica, pessoaJuridica;

    @FXML
    private void incluirCliente(ActionEvent event) {
        if (pessoaFisica.isSelected()) {
            this.cadastrar(new ClientePessoaFisica(this.txtNome.getText(), this.txtDocumento.getText()), event);
        } else if (pessoaJuridica.isSelected()) {
            this.cadastrar(new ClientePessoaJuridica(this.txtNome.getText(), this.txtDocumento.getText()), event);
        }
    }

    private void cadastrar(ClientePessoa cliente, ActionEvent event) {
        if (!this.hasCliente(cliente) && cliente.isDocumentoValido()) {
            this.cliente = cliente;

            Node source = (Node) event.getSource();
            ((Stage) source.getScene().getWindow()).close();
        } else if (!cliente.isDocumentoValido()) {
            this.criarAlerta(Alert.AlertType.WARNING, cliente.getMensagemAlerta()).showAndWait();
        } else {
            this.criarAlerta(Alert.AlertType.WARNING, "Cliente já cadastrado!").showAndWait();
        }
    }

    private boolean hasCliente(ClientePessoa cliente) {
        return this.clientes.stream().filter(e -> e.getNome().equals(cliente.getNome())
                && e.getDocumento().equals(cliente.getDocumento())).findAny().isPresent();
    }

    private Alert criarAlerta(Alert.AlertType type, String text) {
        Alert alert = new Alert(type, text);
        alert.setHeaderText(null);
        return alert;
    }

    public void carregarClientes(List<ClientePessoa> clientes) {
        this.clientes.addAll(clientes);
    }
    
    public Optional<ClientePessoa> getCliente() {
        return Optional.ofNullable(this.cliente);
    }

}
