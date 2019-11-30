package io.vitormac.gestao.registry;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

/**
 *
 * @author vitor
 */
public class PaneRegistry {
    
    private static final Map<String, Pane> REGISTRY = new HashMap<>();
    
    public static Pane forName(String name) {
        return REGISTRY.get(name);
    }
    
    private static void loadToRegistry(String name) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(PaneRegistry.class.getResource(String.format("/views/%s.fxml", name)));
        REGISTRY.put(name, loader.load());
    }
    
    static {
        try {
            PaneRegistry.loadToRegistry("cadastro_cliente");
            PaneRegistry.loadToRegistry("novo_protocolo");
            PaneRegistry.loadToRegistry("main");
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        }
    }
    
    
}
