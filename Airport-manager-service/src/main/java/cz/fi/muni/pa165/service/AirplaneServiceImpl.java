package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dao.AirplaneDao;
import cz.fi.muni.pa165.entities.Airplane;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Jan Cakl
 */
public class AirplaneServiceImpl implements AirplaneService{
    @Autowired
    private AirplaneDao airplaneDao;

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
    
    /**
     * TODO non trivial fce
     */
}
