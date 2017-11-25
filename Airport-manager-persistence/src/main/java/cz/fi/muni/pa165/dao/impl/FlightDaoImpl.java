package cz.fi.muni.pa165.dao.impl;

import cz.fi.muni.pa165.dao.FlightDao;
import cz.fi.muni.pa165.entities.Flight;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
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
        try {
            em.persist(flight);
        } catch (PersistenceException e){
            throw new DataIntegrityViolationException("Error while persisting flight.");
        }
    }

    @Override
    public void deleteFlight(Flight flight) {
        if (flight == null) {
            throw new IllegalArgumentException("Cannot delete null flight");
        }
        try {
            em.remove(flight);
        } catch (PersistenceException e) {
            throw new DataIntegrityViolationException("Error while delete data in database");
        }
    }


    @Override
    public void updateFlight(Flight flight) {
        if (flight == null){
            throw new IllegalArgumentException("Cannot update null flight");
        }
        Flight flightFromDb = getFlight(flight.getId());

        try {
            em.merge(flightFromDb);
        } catch (PersistenceException e){
            throw new DataIntegrityViolationException("Error while updating data in database");
        }
    }

    @Override
    public List<Flight> getAllFlights() {
        return em.createQuery("select f from Flight f", Flight.class).getResultList();
    }

    @Override
    public Flight getFlight(Long id) {
        try {
            return em.find(Flight.class, id);
        } catch (NoResultException nrf) {
            return null;
        }
    }


}
