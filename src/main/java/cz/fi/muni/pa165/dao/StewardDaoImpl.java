package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entities.Steward;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Implementation of StewardDao interface.
 *
 * @author Ondrej Prikryl
 */
@Repository
public class StewardDaoImpl implements StewardDao {

    @PersistenceContext
    private EntityManager em;

    public void createSteward(Steward steward) {
        em.persist(steward);
    }

    public void deleteSteward(Steward steward) {
        em.remove(steward);
    }

    public void updateSteward(Steward steward) {
        Steward stewardFromDb = em.find(Steward.class, steward.getId());

        if (stewardFromDb != null) {
            em.merge(steward);
        } else {
            throw new IllegalArgumentException("Steward: " + steward + " not in the persistence storage.");
        }
    }

    public Steward getSteward(Long id) {
        return em.find(Steward.class, id);
    }

    public List<Steward> listAllStewards() {
        return em.createQuery("SELECT c FROM Steward c", Steward.class).getResultList();
    }
}
