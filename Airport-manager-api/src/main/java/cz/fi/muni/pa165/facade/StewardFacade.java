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

    List<StewardDTO> listAllStewards();

    void deleteSteward(long id);

    void updateStewardName(long id, String firstName, String surName);

    void createSteward(String firstName, String surName);

    List<FlightDTO> getAllStewardsFlightsInTimeRange(long id, LocalDateTime startTime, LocalDateTime stopTime);

    List<FlightDTO> getStewardLastAndCurrentAndFutureFlight(long id);

    List<FlightDTO> getAllStewardFlights(long id);
}
