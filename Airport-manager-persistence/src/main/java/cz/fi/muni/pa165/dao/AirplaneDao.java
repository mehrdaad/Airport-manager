/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entities.Airplane;

import java.util.List;

/**
 * Data Access Object layer for the {@link Airplane} entity.
 *
 * @author Honza
 */
public interface AirplaneDao {

    /**
     * Save a {@link Airplane} entity instance in the persistence storage.
     *
     * @param airplane the {@link Airplane} entity instance
     */
    void addAirplane(Airplane airplane);

    /**
     * Remove the {@link Airplane} entity instance from the persistence storage.
     *
     * @param airplane the {@link Airplane} entity instance
     */
    void deleteAirplane(Airplane airplane);

    /**
     * Update data of the {@link Airplane} entity instance in the persistence storage
     *
     * @param airplane the {@link Airplane} entity instance
     */
    void updateAirplane(Airplane airplane);

    /**
     * Find all instances of the {@link Airplane} entity in the persistence storage.
     *
     * @return a list of {@link Airplane} entities
     */
    List<Airplane> findAll();
    
    /**
     * Find airplanes with given name.
     *
     * @param name name of airplanes
     * @return a list of {@link Airplane} airplanes with given name
     */
    List<Airplane> findByName(String name);

    /**
     * Find airplanes with given type.
     *
     * @param type type of airplanes
     * @return a list of {@link Airplane} airplanes with given type
     */
    List<Airplane> findByType(String type);
    
    /**
     * Find airplanes with given capacity.
     *
     * @param capacity capcity of airplanes
     * @return a list of {@link Airplane} airplanes with given capacity and greater capacity
     */
    List<Airplane> findByCapacityMin(int capacity);
    
    /**
     * Find airplanes with given capacity.
     *
     * @param capacity capacity of airplanes
     * @return a list of {@link Airplane} airplanes with given capacity and less capacity
     */
    List<Airplane> findByCapacityMax(int capacity);
            
    /**
     * Find a {@link Airplane} entity instance with specified <b>id</b>.
     * If the entity instance is contained in the persistence storage, it is returned from there.
     *
     * @param id the id of the {@link Airplane} entity
     * @return the found {@link Airplane} instance or null if the entity does not exist
     */
    Airplane findById(Long id);
    
    

}
