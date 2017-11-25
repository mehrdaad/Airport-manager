package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.AirplaneDTO;
import java.util.List;

/**
 *
 * @author Jan Cakl
 */
public interface AirplaneFacade {
    
    AirplaneDTO findById(Long id);
    
    Long addAirplane(AirplaneDTO airplane);
    
    List<AirplaneDTO> findAll();
    
    void deleteAirplane(Long id);

    void updateAirplane(AirplaneDTO airplane);
    
    /**
     * TODO non trivial fce
     */
    
}
