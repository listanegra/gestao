package io.vitormac.gestao.controller.model;

import javafx.event.ActionEvent;
import javafx.stage.WindowEvent;

/**
 *
 * @author vitor
 */
public interface IGestaoController {
    
    public void confirmaAlteracao(WindowEvent event);
    
    public void incluir(ActionEvent event) throws Exception;
    
    public void excluir(ActionEvent event);
    
}
