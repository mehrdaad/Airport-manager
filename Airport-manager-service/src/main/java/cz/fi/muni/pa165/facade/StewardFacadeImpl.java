package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.FlightDTO;
import cz.fi.muni.pa165.dto.StewardDTO;
import cz.fi.muni.pa165.entities.Steward;
import cz.fi.muni.pa165.service.MappingService;
import cz.fi.muni.pa165.service.StewardService;
import org.apache.commons.collections.list.FixedSizeList;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @author Karel Jiranek
 */
public class StewardFacadeImpl implements StewardFacade {

    @Autowired
    private StewardService stewardService;

    @Autowired
    private MappingService mappingService;

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
        stewardService.updateSteward(mappingService.mapTo(stewardDTO.getId(), Steward.class));
    }

    @Override
    public void createSteward(String firstName, String surName){
        stewardService.createSteward(firstName, surName);
    }

    @Override
    public List<FlightDTO> getAllStewardsFlightsInTimeRange(long id, LocalDateTime startTime, LocalDateTime stopTime){
        return mappingService.mapTo(stewardService.getAllStewardFlightsInTimeRange(id, startTime, stopTime),
                FlightDTO.class);
    }

    @Override
    public List<FlightDTO> getStewardLastAndCurrentAndFutureFlight(long id){
       List<FlightDTO> lastCurrentFutureStewardFlights = FixedSizeList.decorate(Arrays.asList(new FlightDTO[3]));
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





}
