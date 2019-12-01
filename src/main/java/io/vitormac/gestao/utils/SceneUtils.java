package io.vitormac.gestao.utils;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author vitor
 */
public class SceneUtils {

    public static FXMLLoader getLoader(String name) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SceneUtils.class.getResource(String.format("/views/%s.fxml", name)));
        return loader;
    }
    
    public static Scene loadScene(String name) throws IOException {
        return SceneUtils.loadScene(SceneUtils.getLoader(name));
    }
    
    public static Scene loadScene(FXMLLoader loader) throws IOException {
        return new Scene(loader.load());
    }

    public static Stage createDialog(Scene scene, String title) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle(title);
        stage.setScene(scene);
        return stage;
    }
    
}
