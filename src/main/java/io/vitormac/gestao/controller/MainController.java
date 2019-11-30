package io.vitormac.gestao.controller;

import io.vitormac.gestao.registry.PaneRegistry;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author vitor
 */
public class MainController implements Initializable {

    private Scene novoProtocoloScene;
    private Scene cadastroClienteScene;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.novoProtocoloScene = new Scene(PaneRegistry.forName("novo_protocolo"));
        this.cadastroClienteScene = new Scene(PaneRegistry.forName("cadastro_cliente"));
    }

    @FXML
    private void novoProtocolo(ActionEvent event) throws IOException {
        this.showDialog(this.novoProtocoloScene, "Novo protocolo");
    }
    
    @FXML
    private void cadastrarCliente(ActionEvent event) {
        this.showDialog(this.cadastroClienteScene, "Novo cliente");
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
