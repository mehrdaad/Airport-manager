package cz.fi.muni.pa165.dao.impl;

import cz.fi.muni.pa165.dao.DestinationDao;
import cz.fi.muni.pa165.entities.Destination;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Implementation of the {@link DestinationDao} interface.
 *
 * @author Robert Duriancik
 */

@Repository
public class DestinationDaoImpl implements DestinationDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addDestination(Destination destination) {
        em.persist(destination);
    }

    @Override
    public void removeDestination(Destination destination) {
        em.remove(destination);
    }

    @Override
    public void updateDestination(Destination destination) {
        Destination destFromDb = em.find(Destination.class, destination.getId());

        if (destFromDb != null) {
            em.merge(destination);
        } else {
            throw new IllegalArgumentException("Destination: " + destination + " not in the persistence storage.");
        }
    }

    @Override
    public List<Destination> getAllDestinations() {
        return em.createQuery("select d from Destination d", Destination.class).getResultList();
    }

    @Override
    public Destination getDestination(Long id) {
        return em.find(Destination.class, id);
    }
}
