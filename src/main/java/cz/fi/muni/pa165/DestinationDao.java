package cz.fi.muni.pa165;

import java.util.List;

/**
 * @author Robert Duriancik
 */

public interface DestinationDao {
    void create(Destination destination);

    void delete(Destination destination);

    List<Destination> findAll();

    Destination findById(Long id);

}
