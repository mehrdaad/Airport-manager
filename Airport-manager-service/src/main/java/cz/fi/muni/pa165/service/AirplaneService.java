package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.entities.Airplane;
import java.time.LocalDateTime;
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
     * Find all airplanes with given name
     *
     * @param name name of airplane
     *  @return list of all airplanes with given name
     */
    List<Airplane> findByName(String name);
    
    /**
     * Find all airplanes with given type
     *
     * @param type type of airplane
     *  @return list of all airplanes with given type
     */
    List<Airplane> findByType(String type);
    
    /**
     * Find all airplanes with given capacity
     *
     * @param capacity capacity of airplane
     *  @return list of all airplanes with given capacity and greater capacity
     */
    List<Airplane> findByCapacityMin(int capacity);
    
    /**
     * Find all airplanes with given capacity
     *
     * @param capacity capacity of airplane
     *  @return list of all airplanes with given capacity and less capacity
     */
    List<Airplane> findByCapacityMax(int capacity);
    
    /**
     * Check if airplane is used after sinceDateTime
     * 
     * @param sinceDateTime date after which using is checked
     * @return list of all used airplanes after sinceDateTime
     */
    List<Airplane> findByUsedAfterDateTime(LocalDateTime sinceDateTime);
    
    /**
     * Check if airplane is free after sinceDateTime
     * 
     * @param sinceDateTime date after which availibility is checked
     * @return list of all free airplanes after sinceDateTime
     */
    List<Airplane> findByFreeAfterDateTime(LocalDateTime sinceDateTime);
}
