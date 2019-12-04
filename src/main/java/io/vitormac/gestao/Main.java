package io.vitormac.gestao;

import io.vitormac.gestao.controller.GestaoReclamacoesController;
import io.vitormac.gestao.model.Atendente;
import io.vitormac.gestao.utils.DatabaseManager;
import io.vitormac.gestao.utils.SceneUtils;
import java.util.List;
import java.util.Optional;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author vitor
 */
public class Main extends Application {

    private final EntityManager manager = DatabaseManager.createManager();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        List<Atendente> atendentes = this.manager.createNamedQuery("Atendente.listarAtendentes", Atendente.class).getResultList();
        this.criarUsuarioDefault(atendentes).ifPresent(atendentes::add);

        FXMLLoader loader = SceneUtils.getLoader("main");
        primaryStage.setScene(SceneUtils.loadScene(loader));
        GestaoReclamacoesController controller = loader.getController();
        controller.autenticar(atendentes);
        
        primaryStage.setTitle("Gestão de Reclamações");
        primaryStage.show();
    }

    private Optional<Atendente> criarUsuarioDefault(List<Atendente> atendentes) {
        Atendente atendente = new Atendente("admin", "admin");
        if (!atendentes.contains(atendente)) {
            EntityTransaction transaction = this.manager.getTransaction();

            transaction.begin();
            this.manager.persist(atendente);
            transaction.commit();

            return Optional.of(atendente);
        }
        return Optional.empty();
    }

}
