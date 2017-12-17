package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.AirplaneDTO;
import cz.fi.muni.pa165.entities.Airplane;
import cz.fi.muni.pa165.service.AirplaneService;
import cz.fi.muni.pa165.service.MappingService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jan Cakl
 */
@Service
@Transactional
public class AirplaneFacadeImpl implements AirplaneFacade {
    
    
    @Autowired
    private AirplaneService airplaneService;

    @Autowired
    private MappingService mappingService;
    
    @Override
    public AirplaneDTO findById(Long id) {
        return mappingService.mapTo(airplaneService.findById(id), AirplaneDTO.class);
    }

    @Override
    public Long addAirplane(AirplaneDTO airplane) {
       Airplane nAirPlane = mappingService.mapTo(airplane, Airplane.class);
       return airplaneService.addAirplane(nAirPlane);
    }

    @Override
    public List<AirplaneDTO> findAll() {
       return mappingService.mapTo(airplaneService.findAll(),AirplaneDTO.class);
    }

    @Override
    public void deleteAirplane(Long id) {
        airplaneService.deleteAirplane(airplaneService.findById(id));
    }

    @Override
    public void updateAirplane(AirplaneDTO airplane) {
        airplaneService.updateAirplane(mappingService.mapTo(airplane, Airplane.class));
    }

    @Override
    public List<AirplaneDTO> findByName(String name) {
        return mappingService.mapTo(airplaneService.findByName(name),AirplaneDTO.class);
    }

    @Override
    public List<AirplaneDTO> findByType(String type) {
        return mappingService.mapTo(airplaneService.findByType(type),AirplaneDTO.class);
    }

    @Override
    public List<AirplaneDTO> findByCapacityMin(int capacity) {
        return mappingService.mapTo(airplaneService.findByCapacityMin(capacity),AirplaneDTO.class);
    }

    @Override
    public List<AirplaneDTO> findByCapacityMax(int capacity) {
        return mappingService.mapTo(airplaneService.findByCapacityMax(capacity),AirplaneDTO.class);    
    }

    @Override
    public List<AirplaneDTO> findByUsedAfterDateTime(LocalDateTime sinceDateTime) {
        return mappingService.mapTo(airplaneService.findByUsedAfterDateTime(sinceDateTime),AirplaneDTO.class);  
    }

    @Override
    public List<AirplaneDTO> findByFreeAfterDateTime(LocalDateTime sinceDateTime) {
        return mappingService.mapTo(airplaneService.findByFreeAfterDateTime(sinceDateTime),AirplaneDTO.class); 
    }   
}
