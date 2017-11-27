package cz.fi.muni.pa165.dao.impl;

import cz.fi.muni.pa165.dao.AirplaneDao;
import cz.fi.muni.pa165.entities.Airplane;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Implementation of the {@link AirplaneDao} interface.
 *
 * @author Jan Cakl
 */
@Repository
public class AirplaneDaoImpl implements AirplaneDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addAirplane(Airplane airplane) {
        em.persist(airplane);
    }

    @Override
    public void deleteAirplane(Airplane airplane) {
        em.remove(airplane);
    }

    @Override
    public void updateAirplane(Airplane airplane) {
        Airplane airplaneFromDb = em.find(Airplane.class, airplane.getId());

        if (airplaneFromDb != null) {
            em.merge(airplane);
        } else {
            throw new IllegalArgumentException("Airplane: " + airplane + " not in the persistence storage.");
        }
    }

    @Override
    public List<Airplane> findAll() {
        return em.createQuery("select a from Airplane a", Airplane.class).getResultList();
    }

    @Override
    public Airplane findById(Long id) {
        return em.find(Airplane.class, id);
    }

    @Override
    public List<Airplane> findByName(String name) {
        return em.createQuery("select a FROM Airplane a WHERE a.name like :name ", Airplane.class).setParameter("name", name).getResultList();
    }

    @Override
    public List<Airplane> findByType(String type) {
        return em.createQuery("select a FROM Airplane a WHERE a.type = :type", Airplane.class).setParameter("type", type).getResultList();
    }

    @Override
    public List<Airplane> findByCapacityMin(int capacity) {
        return em.createQuery("select a FROM Airplane a WHERE a.capacity >= :capacity", Airplane.class).setParameter("capacity", capacity).getResultList();
    }

    @Override
    public List<Airplane> findByCapacityMax(int capacity) {
        return em.createQuery("select a FROM Airplane a WHERE a.capacity <= :capacity", Airplane.class).setParameter("capacity", capacity).getResultList();
    }

}
