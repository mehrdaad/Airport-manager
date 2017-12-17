package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.FlightDTO;
import cz.fi.muni.pa165.dto.StewardDTO;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Karel Jiranek
 */
public interface StewardFacade {

    /**
     * Get steward.
     *
     * @param id Steward id.
     * @return Steward DTO.
     */
    StewardDTO getSteward(Long id);

    /**
     * Get list of all stewards.
     *
     * @return List of all stewards DTO.
     */
    List<StewardDTO> listAllStewards();

    /**
     * Delete steward.
     *
     * @param id Steward id.
     */
    void deleteSteward(long id);

    /**
     * Update steward.
     *
     * @param steward Steward to be updated.
     */
    void updateSteward(StewardDTO steward);

    /**
     * Create steward.
     *
     * @param firstName First steward name.
     * @param surName Second steward name.
     */
    Long createSteward(String firstName, String surName);

    /**
     * Get all stewards flights in given time range. All departured flights in range are
     * included in result.
     *
     * @param id Steward id.
     * @param startTime Time to start searching from.
     * @param stopTime Time to stop searching.
     * @return All stewards flights in given time range. Empty list if not any.
     */
    List<FlightDTO> getAllStewardsFlightsInTimeRange(long id, LocalDateTime startTime, LocalDateTime stopTime);

    /**
     * Get steward last flight, current flight, first future flight as list.
     *
     * @param id Steward id.
     * @return Steward last flight, current flight, first future flight as list.
     */
    List<FlightDTO> getStewardLastAndCurrentAndFutureFlight(long id);

    /**
     * Get all stewards flights.
     *
     * @param id Steward id.
     * @return All stewards flights in given time range. Empty list if not any.
     */
    List<FlightDTO> getAllStewardFlights(long id);
}
