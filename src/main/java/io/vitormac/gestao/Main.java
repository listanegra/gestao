package io.vitormac.gestao;

import io.vitormac.gestao.utils.SceneUtils;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author vitor
 */
public class Main extends Application {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(SceneUtils.loadScene("main"));
        primaryStage.show();
    }
    
}
