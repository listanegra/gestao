package io.vitormac.gestao.controller.model;

import io.vitormac.gestao.entity.Atendente;
import io.vitormac.gestao.utils.DatabaseManager;
import io.vitormac.gestao.utils.SceneUtils;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author vitor
 */
public abstract class GestaoControllerBase implements IGestaoController, Initializable {

    private final EntityManager manager = DatabaseManager.createManager();
    private boolean pendente = false;

    private final List<Serializable> persistidos = new ArrayList<>();
    private final List<Serializable> removidos = new ArrayList<>();

    @Override
    public final void confirmaAlteracao(WindowEvent event) {
        if (this.pendente) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Deseja salvar alterações?");
            alert.getButtonTypes().setAll(ButtonType.CANCEL, ButtonType.NO, ButtonType.YES);
            alert.setHeaderText(null);

            Optional<ButtonType> option = alert.showAndWait();
            if (option.get().equals(ButtonType.YES)) {
                this.executar();
            } else if (option.get().equals(ButtonType.CANCEL)) {
                event.consume();
            }
        }
    }

    protected void executar() {
        this.persistidos.forEach(this::persistir);
        this.removidos.forEach(this::remover);
        this.pendente = false;
    }

    private <T extends Serializable> void persistir(T objeto) {
        EntityTransaction transaction = this.manager.getTransaction();

        transaction.begin();
        this.manager.persist(objeto);
        transaction.commit();
    }

    private <T extends Serializable> void remover(T objeto) {
        EntityTransaction transaction = this.manager.getTransaction();

        transaction.begin();
        this.manager.remove(objeto);
        transaction.commit();
    }

    protected <T extends Serializable> List<T> consultar(String query, Class<T> classe) {
        return this.manager.createNamedQuery(query, classe).getResultList();
    }

    protected <T extends Serializable> Optional<T> dialogInclusao(String name, String title, List<T> items) throws IOException {
        FXMLLoader loader = SceneUtils.getLoader(name);
        Stage stage = SceneUtils.createDialog(SceneUtils.loadScene(loader), title);
        DialogCadastroBase<T> controller = loader.getController();
        controller.loadItems(items);

        stage.showAndWait();
        controller.get().ifPresent(this::incluirItem);

        return controller.get();
    }

    protected <T extends Serializable> Optional<T> alertaExclusao(T object) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Deseja excluir este item?");
        alert.getButtonTypes().setAll(ButtonType.NO, ButtonType.YES);
        alert.setHeaderText(null);

        Optional<ButtonType> option = alert.showAndWait();
        if (option.get().equals(ButtonType.YES)) {
            this.removidos.add(object);
            this.pendente = true;
            return Optional.of(object);
        }
        return Optional.empty();
    }

    protected <T extends Serializable> void incluirItem(T item) {
        if (!(item instanceof Atendente)) {
            this.persistidos.add(item);
            this.pendente = true;
        }
    }

}
