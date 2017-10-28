package cz.fi.muni.pa165;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class StewardDaoImpl implements StewardDao {

    @PersistenceContext
    private EntityManager em;

    public void createSteward(Steward steward) {

        if(steward == null) {
            throw new IllegalArgumentException("Steward is null");
        }


        em.persist(steward);
    }

    public void deleteSteward(Steward steward) {

        if(steward == null) {
            throw new IllegalArgumentException("Steward is null");
        }

        em.remove(steward);
    }

    public void updateSteward(Steward steward) {

        if(steward == null) {
            throw new IllegalArgumentException("Steward is null");
        }

        em.merge(steward);
    }

    public Steward getSteward(Long id) {

        return em.find(Steward.class, id);
    }

    public List<Steward> listAllStewards() {
        List<Steward> stewards = em.createQuery("SELECT c FROM Steward c", Steward.class).getResultList();
        return stewards;
    }

    public List<Flight> listOfStewardFlights(Steward steward) {
        return null;
    }
}
