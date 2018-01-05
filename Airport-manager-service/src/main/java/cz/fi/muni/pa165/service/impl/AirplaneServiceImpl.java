package cz.fi.muni.pa165.service.impl;

import cz.fi.muni.pa165.dao.AirplaneDao;
import cz.fi.muni.pa165.entities.Airplane;
import cz.fi.muni.pa165.entities.Flight;
import cz.fi.muni.pa165.exceptions.AirplaneDataAccessException;
import cz.fi.muni.pa165.service.AirplaneService;
import cz.fi.muni.pa165.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Jan Cakl
 */
@Service
public class AirplaneServiceImpl implements AirplaneService {
    @Autowired
    private AirplaneDao airplaneDao;
    
    @Autowired
    private FlightService flightService;

    @Override
    public Airplane findById(Long id) {

        try {
            return airplaneDao.findById(id);
        } catch (Exception e) {
            throw new AirplaneDataAccessException("Exception while getting airplane by ID.", e);
        }
    }

    @Override
    public void deleteAirplane(Airplane airplane) {
        try {
            airplaneDao.deleteAirplane(airplane);
        } catch (Exception e) {
            throw new AirplaneDataAccessException("Exception while deleting airplane: " + airplane, e);
        }
    }

    @Override
    public Long addAirplane(Airplane airplane) {
        try {
            airplaneDao.addAirplane(airplane);
            return airplane.getId();
        } catch (Exception e) {
            throw new AirplaneDataAccessException("Exception while adding airplane: " + airplane, e);
        }
    }

    @Override
    public void updateAirplane(Airplane airplane) {
        try {
            airplaneDao.updateAirplane(airplane);
        } catch (Exception e) {
            throw new AirplaneDataAccessException("Exception while updating airplane: " + airplane, e);
        }
        
    }

    @Override
    public List<Airplane> findAll() { 
        try {
            return airplaneDao.findAll();
        } catch (Exception e) {
            throw new AirplaneDataAccessException("Exception while finding all airplanes.", e);
        }
    }
    
    @Override
    public List<Airplane> findByName(String name) {
        try {
            return airplaneDao.findByName(name);
        } catch (Exception e) {
            throw new AirplaneDataAccessException("Exception while finding airplane by name: " + name, e);
        }
    }
    
    @Override
    public List<Airplane> findByType(String type) {
        try {
            return airplaneDao.findByType(type);
        } catch (Exception e) {
            throw new AirplaneDataAccessException("Exception while finding airplane by type: " + type, e);
        }
    }
    
    @Override
    public List<Airplane> findByCapacityMin(int capacity) {
        try {
            return airplaneDao.findByCapacityMin(capacity);
        } catch (Exception e) {
            throw new AirplaneDataAccessException("Exception while finding airplane by capacityMin: " + capacity, e);
        }
    }
    
    @Override
    public List<Airplane> findByCapacityMax(int capacity) {
        try {
            return airplaneDao.findByCapacityMax(capacity);
        } catch (Exception e) {
            throw new AirplaneDataAccessException("Exception while finding airplane by capacityMax: " + capacity, e);
        }
    }

    @Override
    public List<Airplane> findByUsedAfterDateTime(LocalDateTime sinceDateTime) {
      try {
        List<Flight> allFlightSince = flightService.getFlightsSince(sinceDateTime);
        List<Airplane> allAirplanes = airplaneDao.findAll();
        
        if(allFlightSince.isEmpty()){
            return Collections.emptyList();
        }
        
        List<Airplane> allUsedAirPlanes = new ArrayList<>();
                
         for (Airplane airplane : allAirplanes) {
            for (Flight flight : allFlightSince) {
                if (flight.getAirplane() != null && flight.getAirplane().equals(airplane)) {
                    allUsedAirPlanes.add(airplane);
                    break;
                }
            }
        }
        return allUsedAirPlanes;
      }catch (Exception e) {
            throw new AirplaneDataAccessException("Exception while finding airplane by used after time: " + sinceDateTime, e);
        }
    }

    @Override
    public List<Airplane> findByFreeAfterDateTime(LocalDateTime sinceDateTime) {
        try{
        List<Flight> allFlightSince = flightService.getFlightsSince(sinceDateTime);
        List<Airplane> allAirplanes = airplaneDao.findAll();
        
        if(allFlightSince.isEmpty()){
            return allAirplanes;
        }
        
        List<Airplane> allFreeAirPlanes = new ArrayList<>();

        for (Airplane airplane : allAirplanes) {
            boolean used = false;
            for (Flight flight : allFlightSince) {
                if (flight.getAirplane() != null && flight.getAirplane().equals(airplane)) {
                    used = true;
                    break;
                }
            }
                    
            if(!used){
                allFreeAirPlanes.add(airplane);
            }
	}

        return allFreeAirPlanes;
        }catch (Exception e) {
            throw new AirplaneDataAccessException("Exception while finding airplane by free after time: " + sinceDateTime, e);
        }
    }
}
