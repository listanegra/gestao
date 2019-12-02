package io.vitormac.gestao.controller;

import io.vitormac.gestao.controller.model.GestaoControllerBase;
import io.vitormac.gestao.controller.model.IGestaoController;
import io.vitormac.gestao.model.Reclamacao;
import io.vitormac.gestao.utils.SceneUtils;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author vitor
 */
public class GestaoReclamacoesController extends GestaoControllerBase {

    @FXML
    private TableView tabelaReclamacoes;

    @FXML
    private TableColumn protocoloCol, clienteCol, produtoCol, prioridadeCol, statusCol;

    @FXML
    private void abrirGestaoClientes(ActionEvent event) throws IOException {
        this.criarDialogGestao("gestao_clientes", "Gestão de clientes").showAndWait();
    }

    @FXML
    private void abrirGestaoProdutos(ActionEvent event) throws IOException {
        this.criarDialogGestao("gestao_produtos", "Gestão de Produtos").showAndWait();
    }

    @FXML
    @Override
    public void incluir(ActionEvent event) throws Exception {
        this.dialogInclusao("novo_protocolo", "Novo protocolo", this.tabelaReclamacoes.getItems())
                .ifPresent(reclamacao -> {
                    this.tabelaReclamacoes.getItems().add(reclamacao);
                    this.executar();
                });
    }

    @Override
    public void excluir(ActionEvent event) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.protocoloCol.setCellValueFactory(new PropertyValueFactory("protocolo"));
        this.clienteCol.setCellValueFactory(new PropertyValueFactory("cliente"));
        this.produtoCol.setCellValueFactory(new PropertyValueFactory("produto"));
        this.prioridadeCol.setCellValueFactory(new PropertyValueFactory("prioridade"));
        this.statusCol.setCellValueFactory(new PropertyValueFactory("status"));
        
        List<Reclamacao> clientes = this.consultar("Reclamacao.listarReclamacoes", Reclamacao.class);
        this.tabelaReclamacoes.setItems(FXCollections.observableArrayList(clientes));
    }

    private Stage criarDialogGestao(String name, String title) throws IOException {
        FXMLLoader loader = SceneUtils.getLoader(name);
        Stage stage = SceneUtils.createDialog(SceneUtils.loadScene(loader), title);
        IGestaoController controller = loader.getController();
        stage.setOnCloseRequest(controller::confirmaAlteracao);
        return stage;
    }

}
