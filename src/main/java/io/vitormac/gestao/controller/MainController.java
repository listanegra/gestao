package io.vitormac.gestao.controller;

import io.vitormac.gestao.utils.DatabaseManager;
import io.vitormac.gestao.utils.SceneLoader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Modality;
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
        this.showDialog(SceneLoader.loadScene("novo_protocolo"), "Novo protocolo");
    }

    @FXML
    private void cadastrarCliente(ActionEvent event) throws IOException {
        this.showDialog(SceneLoader.loadScene("cadastro_cliente"), "Novo cliente");
    }

    private void showDialog(Scene scene, String title) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.showAndWait();
    }

}
