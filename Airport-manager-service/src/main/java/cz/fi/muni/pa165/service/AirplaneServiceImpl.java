package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dao.AirplaneDao;
import cz.fi.muni.pa165.entities.Airplane;
import cz.fi.muni.pa165.entities.Flight;
import java.time.LocalDateTime;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import org.springframework.stereotype.Service;

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
        return airplaneDao.findById(id);
    }

    @Override
    public void deleteAirplane(Airplane airplane) {
        airplaneDao.deleteAirplane(airplane);
    }

    @Override
    public void addAirplane(Airplane airplane) {
        airplaneDao.addAirplane(airplane);
    }

    @Override
    public void updateAirplane(Airplane airplane) {
        airplaneDao.updateAirplane(airplane);
    }

    @Override
    public List<Airplane> findAll() {
        return airplaneDao.findAll();
    }
    
    @Override
    public List<Airplane> findByName(String name) {
        return airplaneDao.findByName(name);
    }
    
    @Override
    public List<Airplane> findByType(String type) {
        return airplaneDao.findByType(type);
    }
    
    @Override
    public List<Airplane> findByCapacityMin(int capacity) {
        return airplaneDao.findByCapacityMin(capacity);
    }
    
    @Override
    public List<Airplane> findByCapacityMax(int capacity) {
        return airplaneDao.findByCapacityMax(capacity);
    }

    @Override
    public List<Airplane> findByUsedAfterDateTime(LocalDateTime sinceDateTime) {
      
        List<Flight> allFlightSince = flightService.getFlightsSince(sinceDateTime);
        List<Airplane> allAirplanes = airplaneDao.findAll();
        
        if(allFlightSince.isEmpty()){
            return Collections.emptyList();
        }
        
        List<Airplane> allUsedAirPlanes = allAirplanes;
                
         for (Airplane airplane : allAirplanes) {
            for (Flight flight : allFlightSince) {
                if (flight.getAirPlane() != null && !flight.getAirPlane().equals(airplane)) {
                    allUsedAirPlanes.remove(airplane);
                }
            }
        }
        return allUsedAirPlanes;
    }

    @Override
    public List<Airplane> findByFreeAfterDateTime(LocalDateTime sinceDateTime) {
        List<Flight> allFlightSince = flightService.getFlightsSince(sinceDateTime);
        List<Airplane> allAirplanes = airplaneDao.findAll();
        
        if(allFlightSince.isEmpty()){
            return allAirplanes;
        }
        
        List<Airplane> allFreeAirPlanes = allAirplanes;
                
         for (Airplane airplane : allAirplanes) {
            for (Flight flight : allFlightSince) {
                if (flight.getAirPlane() != null && flight.getAirPlane().equals(airplane)) {
                    allFreeAirPlanes.remove(airplane);
                }
            }
        }
        return allFreeAirPlanes;
    }

}
