package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.entities.Destination;
import cz.fi.muni.pa165.entities.Flight;
import cz.fi.muni.pa165.entities.Steward;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    Steward findStewardById(Long id);

    /**
     * This method returns all stewards stored in the database.
     *
     * @return List of all stewards currently stored in database.
     */
    List<Steward> listAllStewards();

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
    List<String> getAllStewardsNameOrdered();

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
    void updateStewardName(long id, String firstName, String surName);

    /**
     * Get all flights that given steward served, serving and going to serve.
     *
     * @param id Steward id.
     * @return All flights that given steward served. Empty list if not any.
     */
    List<Flight> getAllStewardsFlights(long id);

    /**
     * Get all flights that given steward going to serve in future. Only not departured flights are
     * included in result.
     *
     * @param id Steward id.
     * @return All flights that given steward going to serve in future. Empty list if not any.
     */
    List<Flight> getAllStewardsFutureFlights(long id);

    /**
     * Get all stewards departured flights after given time.
     *
     * @param id Steward id.
     * @param startTime Time to start searching from.
     * @return All stewards flights after given time (date). Empty list if not any.
     */
    List<Flight> getAllStewardsFlightsAfter(long id, LocalDateTime startTime);

    /**
     * Get all stewards departured flights before given time. Flight in the air is
     * included if departured before give time.
     *
     * @param id Steward id.
     * @param stopTime Time to stop searching.
     * @return All stewards flights before given time (date). Empty list if not any.
     */
    List<Flight> getAllStewardsFlightsBefore(long id, LocalDateTime stopTime);

    /**
     * Get all stewards flights in given time range. All departured flights in range are
     * included in result.
     *
     * @param id Steward id.
     * @param startTime Time to start searching from.
     * @param stopTime Time to stop searching.
     * @return All stewards flights in given time range. Empty list if not any.
     */
    List<Flight> getAllStewardsFlightsInTimeRange(long id, LocalDateTime startTime, LocalDateTime stopTime);

    /**
     * Get the last stewards flight. If the steward is in the air that flight is considered as the last flight.
     *
     * @param id Steward id.
     * @return Last stewards flight. Null if no flight found.
     */
    Flight getStewardsLastFlight(long id);

    /**
     * Get flight the steward is currently serving. Steward is in the air.
     *
     * @param id Steward id.
     * @return Flight the steward is currently serving. Null if stewards is not in the air.
     */
    Flight getCurrentStewardFlight(long id);

    /**
     * Check if stewards is in the air. If stewards is departured flight and
     * airplane is still or already on the land it is considered as in the air.
     *
     * @param id Steward id.
     * @return True if stewards is in the air (serving flight).
     */
    boolean isStewardInTheAir(long id);

}
