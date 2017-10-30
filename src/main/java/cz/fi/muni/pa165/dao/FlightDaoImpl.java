package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entities.Flight;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Implementation of the {@link FlightDao} interface.
 *
 * @author Karel Jiranek
 */
@Repository
public class FlightDaoImpl implements FlightDao  {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Flight flight) {
        em.persist(flight);
    }

    @Override
    public void delete(Flight flight) {
        em.remove(flight);
    }

    @Override
    public void update(Flight flight) {
        Flight flightFromDb = em.find(Flight.class, flight.getId());

        if (flightFromDb != null) {
            em.merge(flightFromDb);
        } else {
            throw new IllegalArgumentException("Destination: " + flight + " not in the persistence storage.");
        }
    }

    @Override
    public List<Flight> findAll() {
        return em.createQuery("select f from Flight f", Flight.class).getResultList();
    }

    @Override
    public Flight findById(Long id) {
        return em.find(Flight.class, id);
    }



}
