package io.vitormac.gestao.controller;

import io.vitormac.gestao.controller.model.GestaoControllerBase;
import io.vitormac.gestao.controller.model.IGestaoController;
import io.vitormac.gestao.entity.Atendente;
import io.vitormac.gestao.entity.ClientePessoa;
import io.vitormac.gestao.entity.Produto;
import io.vitormac.gestao.entity.Reclamacao;
import io.vitormac.gestao.utils.SceneUtils;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author vitor
 */
public class GestaoReclamacoesController extends GestaoControllerBase {

    private static Atendente ATENDENTE;

    public static Atendente atendente() {
        return GestaoReclamacoesController.ATENDENTE;
    }

    @FXML
    private TableView<Reclamacao> tabelaReclamacoes;

    @FXML
    private TableColumn<Reclamacao, Integer> protocoloCol;

    @FXML
    private TableColumn<Reclamacao, ClientePessoa> clienteCol;

    @FXML
    private TableColumn<Reclamacao, Produto> produtoCol;

    @FXML
    private TableColumn<Reclamacao, Reclamacao.Prioridade> prioridadeCol;

    @FXML
    private TableColumn<Reclamacao, Reclamacao.Status> statusCol;

    @FXML
    private TableColumn<Reclamacao, Atendente> atendenteCol;

    @FXML
    private Button btnExibirDetalhes;

    @FXML
    private ListView<Reclamacao> listaProtocolos;

    @FXML
    private void abrirGestaoClientes(ActionEvent event) throws IOException {
        this.criarDialogGestao("gestao_clientes", "Gestão de clientes").showAndWait();
    }

    @FXML
    private void abrirGestaoProdutos(ActionEvent event) throws IOException {
        this.criarDialogGestao("gestao_produtos", "Gestão de Produtos").showAndWait();
    }

    @FXML
    private void exibirDetalhes(ActionEvent event) {

    }

    @FXML
    @Override
    public void incluir(ActionEvent event) throws Exception {
        this.dialogInclusao("novo_protocolo", "Novo protocolo", this.tabelaReclamacoes.getItems())
                .ifPresent(reclamacao -> {
                    this.tabelaReclamacoes.getItems().add(reclamacao);
                    this.listaProtocolos.getItems().add(reclamacao);
                    this.executar();
                });
    }

    @Override
    public void excluir(ActionEvent event) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.protocoloCol.setCellValueFactory(new PropertyValueFactory<>("protocolo"));
        this.clienteCol.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        this.produtoCol.setCellValueFactory(new PropertyValueFactory<>("produto"));
        this.prioridadeCol.setCellValueFactory(new PropertyValueFactory<>("prioridade"));
        this.statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        this.atendenteCol.setCellValueFactory(new PropertyValueFactory<>("atendente"));

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

    public void autenticar(List<Atendente> atendentes) throws IOException {
        Optional<Atendente> optional = this.dialogInclusao("login", "Entrar", atendentes);
        optional.ifPresent(a -> {
            GestaoReclamacoesController.ATENDENTE = a;
            this.listaProtocolos.setItems(FXCollections.observableArrayList(a.getReclamacoes()));
        });
        if (!optional.isPresent()) System.exit(0);
    }

}
