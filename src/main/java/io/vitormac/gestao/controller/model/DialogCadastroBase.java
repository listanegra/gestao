package io.vitormac.gestao.controller.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 *
 * @author vitor
 */
public abstract class DialogCadastroBase<T> {

    private T object;
    private final List<T> items = new ArrayList<>();

    protected abstract void action(ActionEvent event);
    
    protected abstract boolean validate(T object);

    protected void doAction(T object, ActionEvent event) {
        if (this.validate(object)) {
            this.object = object;

            Node source = (Node) event.getSource();
            ((Stage) source.getScene().getWindow()).close();
        }
    }
    
    protected boolean exists(Predicate<? super T> predicate) {
        return this.items.stream().filter(predicate).findAny().isPresent();
    }

    protected Alert alert(String text) {
        Alert alert = new Alert(Alert.AlertType.WARNING, text);
        alert.setHeaderText(null);
        return alert;
    }
    
    protected List<T> getItems() {
        return this.items;
    }

    public void loadItems(List<T> items) {
        this.items.addAll(items);
    }

    public Optional<T> get() {
        return Optional.ofNullable(this.object);
    }
    
}
