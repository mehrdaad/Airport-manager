package cz.fi.muni.pa165.rest.controllers;

import cz.fi.muni.pa165.dto.DestinationDTO;
import cz.fi.muni.pa165.facade.DestinationFacade;
import cz.fi.muni.pa165.rest.Uris;
import cz.fi.muni.pa165.rest.exceptions.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

/** *
 * @author Karel Jiranek
 */
@RestController
@RequestMapping(Uris.ROOT_URI_DESTINATIONS)
public class DestinationsController {
    @Inject
    private DestinationFacade destinationFacade;


    /**
     * Get destinations
     *
     * @return All destinations
     */
    @RequestMapping(/*value = "/all",*/ method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<DestinationDTO> getDestinations() {
        return destinationFacade.getAllDestinations();
    }

    /**
     * Get destination by id.
     * @param id Id of destination.
     * @return Destination with given destination or raise the exception.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final DestinationDTO getDestinationsByID(@PathVariable("id") long id)  {
        DestinationDTO DestinationDTO = destinationFacade.getDestinationById(id);
        if (DestinationDTO != null) {
            return DestinationDTO;
        } else {
            throw new ResourceNotFoundException();
        }
    }

    //  destinationFacade.getDestinationsByCity()
    /**
     * Get all destinations by city.
     * @param city City of destination.
     * @return Destination with given destination and city.
     */
    @RequestMapping(value = "/all/{city}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<DestinationDTO> getDestinationsByCity(@PathVariable("city") String city)  {
        return destinationFacade.getDestinationsByCity(city);
    }

}
