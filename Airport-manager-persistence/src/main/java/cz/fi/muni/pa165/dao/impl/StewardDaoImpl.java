package cz.fi.muni.pa165.dao.impl;

import cz.fi.muni.pa165.dao.StewardDao;
import cz.fi.muni.pa165.entities.Steward;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Implementation of {@link StewardDao} interface.
 *
 * @author Ondrej Prikryl
 */
@Repository
public class StewardDaoImpl implements StewardDao {

    @PersistenceContext
    private EntityManager em;

    public void createSteward(Steward steward) {
        if(steward == null) {
            throw new NullPointerException("Steward is null.");
        }

        if(steward.getFirstName() == null || steward.getSurname() == null) {
            throw new IllegalArgumentException("Steward's firstname or surname is null.");
        }

        em.persist(steward);
    }

    public void deleteSteward(Steward steward) {
        if(steward == null) {
            throw new NullPointerException("Steward is null.");
        }

        em.remove(steward);
    }

    public void updateSteward(Steward steward) {
        if(steward == null) {
            throw new NullPointerException("Steward is null.");
        }
        if(steward.getFirstName() == null || steward.getSurname() == null) {
            throw new IllegalArgumentException("Steward's firstname or surname is null.");
        }

        Steward stewardFromDb = em.find(Steward.class, steward.getId());
        if (stewardFromDb != null) {
            em.merge(steward);
        } else {
            throw new IllegalArgumentException("Steward: " + steward + " not in the persistence storage.");
        }
    }

    public Steward getSteward(Long id) {
        if(id == null) {
            throw new IllegalArgumentException("ID is null.");
        }
        return em.find(Steward.class, id);
    }

    public List<Steward> listAllStewards() {
        return em.createQuery("SELECT c FROM Steward c", Steward.class).getResultList();
    }
}
