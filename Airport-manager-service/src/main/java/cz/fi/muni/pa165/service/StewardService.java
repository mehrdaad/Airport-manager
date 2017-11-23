package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.entities.Flight;
import cz.fi.muni.pa165.entities.Steward;

import java.time.LocalDateTime;
import java.util.List;

/**
 * An interface that defines a service access to the {@link Steward} entity.
 *
 * @author Karel Jiranek
 */
public interface StewardService {
    /**
     * Get steward by id.
     * @param id Id identifying steward.
     * @return Steward with given id.
     */
    Steward getSteward(Long id);

    /**
     * Delete steward
     *
     * @param steward Steward to delete.
     */
    void deleteSteward(Steward steward);

    /**
     * Get all stewards full names ordered by surname/first name.
     * Surname is first in full name.
     *
     * @return Ordered list of stewards full names.
     */
    List<Steward> getAllStewardsNameOrdered();

    /**
     * Create steward with name and surname.
     *
     * @param firstName Steward first name.
     * @param surName Steward surname.
     */
    void createSteward(String firstName, String surName);

    /**
     * Update steward name (first name and surname).
     *
     * @param id Steward's id
     * @param firstName New first name.
     * @param surName New surname.
     */
    void updateSteward(Steward steward);

    /**
     * Get all flights that given steward served, serving and going to serve.
     *
     * @param id Steward id.
     * @return All flights that given steward served. Empty list if not any.
     */
    List<Flight> getAllStewardFlights(long id);

    /**
     * Get all stewards flights in given time range. All departured flights in range are
     * included in result.
     *
     * @param id Steward id.
     * @param startTime Time to start searching from.
     * @param stopTime Time to stop searching.
     * @return All stewards flights in given time range. Empty list if not any.
     */
    List<Flight> getAllStewardFlightsInTimeRange(long id, LocalDateTime startTime, LocalDateTime stopTime);

    /**
     * Get the last stewards flight. If the steward is in the air that flight is considered as the last flight.
     *
     * @param id Steward id.
     * @return Last stewards flight. Null if no flight found.
     */
    Flight getStewardLastFlight(long id);

    /**
     * Get flight the steward is currently serving. Steward is in the air.
     *
     * @param id Steward id.
     * @return Flight the steward is currently serving. Null if stewards is not in the air.
     */
    Flight getStewardCurrentFlight(long id);

    /**
     * Get all flights that given steward going to serve in future. Only not departured flights are
     * included in result.
     *
     * @param id Steward id.
     * @return All flights that given steward going to serve in future. Empty list if not any.
     */
    Flight getStewardFutureFlight(long id);


}
