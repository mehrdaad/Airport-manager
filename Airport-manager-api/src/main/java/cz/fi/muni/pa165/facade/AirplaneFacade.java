package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.AirplaneDTO;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Jan Cakl
 */
public interface AirplaneFacade {
    
    AirplaneDTO findById(Long id);
    
    Long addAirplane(AirplaneDTO airplane);
    
    List<AirplaneDTO> findAll();
    
    List<AirplaneDTO> findByName(String name);
    
    List<AirplaneDTO> findByType(String type);
    
    List<AirplaneDTO> findByCapacityMin(int capacity);
    
    List<AirplaneDTO> findByCapacityMax(int capacity);
    
    List<AirplaneDTO> findByUsedAfterDateTime(LocalDateTime sinceDateTime);
    
    List<AirplaneDTO> findByFreeAfterDateTime(LocalDateTime sinceDateTime);
    
    void deleteAirplane(Long id);

    void updateAirplane(AirplaneDTO airplane);
}
