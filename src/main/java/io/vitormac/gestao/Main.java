package io.vitormac.gestao;

import io.vitormac.gestao.registry.PaneRegistry;
import javafx.application.Application;
import javafx.scene.Scene;
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
        primaryStage.setScene(new Scene(PaneRegistry.forName("main")));
        primaryStage.show();
    }
    
}
