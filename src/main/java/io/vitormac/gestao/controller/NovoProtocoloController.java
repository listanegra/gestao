package io.vitormac.gestao.controller;

import io.vitormac.gestao.controller.model.DialogCadastroBase;
import io.vitormac.gestao.utils.DatabaseManager;
import io.vitormac.gestao.entity.ClientePessoa;
import io.vitormac.gestao.entity.Produto;
import io.vitormac.gestao.entity.Reclamacao;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javax.persistence.EntityManager;

/**
 *
 * @author vitor
 */
public class NovoProtocoloController extends DialogCadastroBase<Reclamacao> implements Initializable {

    private final EntityManager manager = DatabaseManager.createManager();

    @FXML
    private TextArea txtDescricao;

    @FXML
    private ComboBox<ClientePessoa> cbClientes;

    @FXML
    private ComboBox<Produto> cbProdutos;

    @FXML
    private ComboBox<Reclamacao.Prioridade> cbPrioridade;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<ClientePessoa> clientes = manager.createNamedQuery("Cliente.listarClientes", ClientePessoa.class).getResultList();
        this.cbClientes.setItems(FXCollections.observableArrayList(clientes));

        List<Produto> produtos = manager.createNamedQuery("Produto.listarProdutos", Produto.class).getResultList();
        this.cbProdutos.setItems(FXCollections.observableArrayList(produtos));

        this.cbPrioridade.setItems(FXCollections.observableArrayList(Reclamacao.Prioridade.values()));
    }

    @FXML
    @Override
    protected void action(ActionEvent event) {
        this.doAction(new Reclamacao(this.txtDescricao.getText(), this.cbClientes.getValue(),
                this.cbProdutos.getValue(), GestaoReclamacoesController.atendente(), this.cbPrioridade.getValue()), event);
    }

    @Override
    protected boolean validate(Reclamacao reclamacao) {
        if (reclamacao.getDescricao().isEmpty()) {
            this.alert("Forneça uma descrição para o problema!");
            return false;
        }

        if (reclamacao.getCliente() == null) {
            this.alert("Selecione um cliente!");
            return false;
        }

        if (reclamacao.getProduto() == null) {
            this.alert("Selecione um produto!");
            return false;
        }

        if (reclamacao.getPrioridade() == null) {
            this.alert("Selecione uma prioridade!");
            return false;
        }

        if (this.exists(e -> e.getCliente().equals(reclamacao.getCliente())
                && e.getProduto().equals(reclamacao.getProduto())
                && e.getStatus().equals(Reclamacao.Status.PENDENTE))) {
            this.alert(String.format("Já existe um protocolo pendente para o cliente %s!", reclamacao.getCliente().getNome()));
            return false;
        }

        return true;
    }

}
