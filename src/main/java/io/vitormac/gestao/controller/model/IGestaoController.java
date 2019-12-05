package io.vitormac.gestao.controller.model;

import javafx.event.ActionEvent;
import javafx.stage.WindowEvent;

/**
 *
 * @author vitor
 */
public interface IGestaoController {
    
    void confirmaAlteracao(WindowEvent event);
    
    void incluir(ActionEvent event) throws Exception;
    
    void excluir(ActionEvent event);
    
}
