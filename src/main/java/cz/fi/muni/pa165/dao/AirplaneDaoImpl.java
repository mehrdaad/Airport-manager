/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.dao;
import cz.fi.muni.pa165.entities.Airplane;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jan Cakl
 */
@Repository
public class AirplaneDaoImpl implements AirplaneDao{
    
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
    
}
