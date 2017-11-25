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

    StewardDTO getSteward(Long id);

    List<StewardDTO> listAllStewards();

    void deleteSteward(long id);

    void updateSteward(StewardDTO steward);

    void createSteward(String firstName, String surName);

    List<FlightDTO> getAllStewardsFlightsInTimeRange(long id, LocalDateTime startTime, LocalDateTime stopTime);

    List<FlightDTO> getStewardLastAndCurrentAndFutureFlight(long id);

    List<FlightDTO> getAllStewardFlights(long id);
}
