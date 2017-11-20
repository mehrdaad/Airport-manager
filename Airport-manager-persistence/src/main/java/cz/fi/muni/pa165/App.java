package cz.fi.muni.pa165;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Application
 */
public class App {
    private static EntityManagerFactory emf;

    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(PersistenceConfig.class);

        emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        em.close();
        emf.close();
    }
}
