package io.vitormac.gestao.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author vitor
 */
public class DatabaseManager {

    private static final EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("GestaoReclamacoes");

    public static EntityManager createManager() {
        return FACTORY.createEntityManager();
    }

}
