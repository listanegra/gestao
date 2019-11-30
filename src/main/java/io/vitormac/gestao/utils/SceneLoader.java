package io.vitormac.gestao.utils;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

/**
 *
 * @author vitor
 */
public class SceneLoader {

    public static Scene loadScene(String name) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SceneLoader.class.getResource(String.format("/views/%s.fxml", name)));
        return new Scene(loader.load());
    }

}
