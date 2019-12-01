package io.vitormac.gestao.controller;

import io.vitormac.gestao.controller.model.IGestaoController;
import io.vitormac.gestao.utils.DatabaseManager;
import io.vitormac.gestao.utils.SceneUtils;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javax.persistence.EntityManager;

/**
 *
 * @author vitor
 */
public class MainController implements Initializable {

    private EntityManager manager;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.manager = DatabaseManager.createManager();
    }

    @FXML
    private void novoProtocolo(ActionEvent event) throws IOException {
        SceneUtils.createDialog(SceneUtils.loadScene("novo_protocolo"), "Novo protocolo").showAndWait();
    }

    @FXML
    private void abrirGestaoClientes(ActionEvent event) throws IOException {
        this.criarDialogGestao("gestao_clientes", "Gestão de clientes").showAndWait();
    }
    
    @FXML
    private void abrirGestaoProdutos(ActionEvent event) throws IOException {
        this.criarDialogGestao("gestao_produtos", "Gestão de Produtos").showAndWait();
    }
    
    private Stage criarDialogGestao(String name, String title) throws IOException {
        FXMLLoader loader = SceneUtils.getLoader(name);
        Stage stage = SceneUtils.createDialog(SceneUtils.loadScene(loader), title);
        IGestaoController controller = loader.getController();
        stage.setOnCloseRequest(controller::confirmaAlteracao);
        return stage;
    }

}
