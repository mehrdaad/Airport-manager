package cz.fi.muni.pa165.dao.impl;

import cz.fi.muni.pa165.dao.FlightDao;
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
public class FlightDaoImpl implements FlightDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addFlight(Flight flight) {
        if (flight == null){
            throw new IllegalArgumentException("Cannot create null flight");
        }
        em.persist(flight);
    }

    @Override
    public void deleteFlight(Flight flight) {
        if (flight == null){
            throw new IllegalArgumentException("Cannot delete null flight");
        }
        em.remove(flight);
    }

    @Override
    public void updateFlight(Flight flight) {
        if (flight == null){
            throw new IllegalArgumentException("Cannot update null flight");
        }
        Flight flightFromDb = em.find(Flight.class, flight.getId());

        if (flightFromDb != null) {
            em.merge(flightFromDb);
        } else {
            throw new IllegalArgumentException("Destination: " + flight + " not in the persistence storage.");
        }
    }

    @Override
    public List<Flight> getAllFlight() {
        return em.createQuery("select f from Flight f", Flight.class).getResultList();
    }

    @Override
    public Flight getFlight(Long id) {
        return em.find(Flight.class, id);
    }


}
