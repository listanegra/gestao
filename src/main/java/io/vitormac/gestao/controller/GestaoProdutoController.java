package io.vitormac.gestao.controller;

import io.vitormac.gestao.controller.model.GestaoControllerBase;
import io.vitormac.gestao.entity.Produto;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

/**
 *
 * @author vitor
 */
public class GestaoProdutoController extends GestaoControllerBase {

    @FXML
    private TableColumn<Produto, String> nomeCol;

    @FXML
    private TableColumn<Produto, String> marcaCol;

    @FXML
    private TableColumn<Produto, String> descricaoCol;

    @FXML
    private TableView<Produto> tableProdutos;

    @FXML
    private void alterarNome(CellEditEvent<Produto, String> event) {
        Produto produto = event.getRowValue();
        if (!event.getNewValue().equals(event.getOldValue())) {
            produto.setNome(event.getNewValue());
            this.incluirItem(produto);
        }
    }

    @FXML
    private void alterarMarca(CellEditEvent<Produto, String> event) {
        Produto produto = event.getRowValue();
        if (!event.getNewValue().equals(event.getOldValue())) {
            produto.setMarca(event.getNewValue());
            this.incluirItem(produto);
        }
    }

    @FXML
    @Override
    public void incluir(ActionEvent event) throws Exception {
        this.dialogInclusao("cadastro_produto", "Novo produto", this.tableProdutos.getItems())
                .ifPresent(this.tableProdutos.getItems()::add);
    }

    @FXML
    @Override
    public void excluir(ActionEvent event) {
        Produto produto = this.tableProdutos.getSelectionModel().getSelectedItem();
        this.alertaExclusao(produto).ifPresent(this.tableProdutos.getItems()::remove);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.nomeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        this.nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
        this.marcaCol.setCellFactory(TextFieldTableCell.forTableColumn());
        this.marcaCol.setCellValueFactory(new PropertyValueFactory<>("marca"));
        this.descricaoCol.setCellFactory(TextFieldTableCell.forTableColumn());
        this.descricaoCol.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        List<Produto> produtos = this.consultar("Produto.listarProdutos", Produto.class);
        this.tableProdutos.setItems(FXCollections.observableArrayList(produtos));
    }

}
