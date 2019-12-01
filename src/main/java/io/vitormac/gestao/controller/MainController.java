package io.vitormac.gestao.controller;

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
        FXMLLoader loader = SceneUtils.getLoader("gestao_clientes");
        Stage stage = SceneUtils.createDialog(SceneUtils.loadScene(loader), "Gestão de clientes");
        GestaoClienteController controller = loader.getController();
        
        stage.setOnCloseRequest(controller::confirmaAlteracao);
        stage.showAndWait();
    }

}
