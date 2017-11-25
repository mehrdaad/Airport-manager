package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.entities.Airplane;
import java.util.List;

/**
 *
 * @author Jan Cakl
 */
public interface AirplaneService {
    
    /**
     * Find airplane by id.
     * @param id Id identifying airplane.
     * @return Airplane with given id.
     */
    Airplane findById(Long id);
    
    /**
     * Delete airplane
     *
     * @param airplane Airplane to delete.
     */
    void deleteAirplane(Airplane airplane);
    
    /**
     * Add airplane 
     *
     *  @param airplane Airplane to add.
     */
    void addAirplane(Airplane airplane);
    
    /**
     * Update airplane.
     *
     *  @param airplane Airplane to update.
     */
    void updateAirplane(Airplane airplane);
    
    /**
     * Find all planes
     *
     *  @return list of all planes
     */
    List<Airplane> findAll();
    
    /**
    *   TODO non trivial fce
    */
}
