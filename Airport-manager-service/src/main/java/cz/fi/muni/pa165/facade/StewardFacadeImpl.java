package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.FlightDTO;
import cz.fi.muni.pa165.dto.StewardDTO;
import cz.fi.muni.pa165.entities.Steward;
import cz.fi.muni.pa165.service.FlightService;
import cz.fi.muni.pa165.service.MappingService;
import cz.fi.muni.pa165.service.StewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Karel Jiranek
 */
@Service
@Transactional
public class StewardFacadeImpl implements StewardFacade {

    @Autowired
    private StewardService stewardService;

    @Autowired
    private FlightService flightService;

    @Autowired
    private MappingService mappingService;

    @Override
    public StewardDTO getSteward(Long id) {
        return mappingService.mapTo(stewardService.getSteward(id), StewardDTO.class);
    }

    @Override
    public List<StewardDTO> listAllStewards(){
        return mappingService.mapTo(stewardService.getAllStewardsNameOrdered(), StewardDTO.class);
    }

    @Override
    public void deleteSteward(long id){
        stewardService.deleteSteward(stewardService.getSteward(id));
    }

    @Override
    public void updateSteward(StewardDTO stewardDTO){
        stewardService.updateSteward(mappingService.mapTo(stewardDTO, Steward.class));
    }

    @Override
    public Long createSteward(String firstName, String surName){
        return stewardService.createSteward(firstName, surName);
    }

    @Override
    public List<FlightDTO> getAllStewardsFlightsInTimeRange(long id, LocalDateTime startTime, LocalDateTime stopTime){
        return mappingService.mapTo(stewardService.getAllStewardFlightsInTimeRange(id, startTime, stopTime),
                FlightDTO.class);
    }

    @Override
    public List<FlightDTO> getStewardLastAndCurrentAndFutureFlight(long id){
       List<FlightDTO> lastCurrentFutureStewardFlights = new ArrayList<>(3);
       lastCurrentFutureStewardFlights.add(0,
               mappingService.mapTo(stewardService.getStewardCurrentFlight(id), FlightDTO.class));
       lastCurrentFutureStewardFlights.add(1,
                mappingService.mapTo(stewardService.getStewardLastFlight(id), FlightDTO.class));
       lastCurrentFutureStewardFlights.add(2,
                mappingService.mapTo(stewardService.getStewardFutureFlight(id), FlightDTO.class));
       return lastCurrentFutureStewardFlights;
    }

    @Override
    public List<FlightDTO> getAllStewardFlights(long id){
        return mappingService.mapTo(stewardService.getAllStewardFlights(id),
                FlightDTO.class);
    }

    @Override
    public List<StewardDTO> getFreeStewardsInTimeRange(LocalDateTime start, LocalDateTime end) {
        return mappingService.mapTo(stewardService.getFreeStewardsInTimeRange(start, end), StewardDTO.class);
    }

}
