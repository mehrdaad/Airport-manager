package cz.fi.muni.pa165;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Robert Duriancik
 */

@Repository
public class DestinationDaoImpl implements DestinationDao {

    @PersistenceContext
    private EntityManager em;

    public void create(Destination destination) {
        em.persist(destination);
    }

    public void delete(Destination destination) {
        em.remove(destination);
    }

    public void update(Destination destination) {
        Destination destFromDb = em.find(Destination.class, destination.getId());

        if (destFromDb != null) {
            em.merge(destination);
        } else {
            throw new IllegalArgumentException("Destination: " + destination + " not in the persistence storage.");
        }
    }

    public List<Destination> findAll() {
        return em.createQuery("select d from Destination d", Destination.class).getResultList();
    }

    public Destination findById(Long id) {
        return em.find(Destination.class, id);
    }
}
