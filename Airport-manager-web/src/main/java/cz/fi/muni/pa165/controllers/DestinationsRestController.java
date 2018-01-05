package cz.fi.muni.pa165.controllers;


import cz.fi.muni.pa165.dto.DestinationDTO;
import cz.fi.muni.pa165.exceptions.InvalidRequestException;
import cz.fi.muni.pa165.exceptions.ResourceNotFoundException;
import cz.fi.muni.pa165.facade.DestinationFacade;
import cz.fi.muni.pa165.facade.FlightFacade;
import cz.fi.muni.pa165.hateoas.DestinationResource;
import cz.fi.muni.pa165.hateoas.DestinationResourceAssembler;
import cz.fi.muni.pa165.hateoas.FlightResource;
import cz.fi.muni.pa165.hateoas.FlightResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Karel Jiranek
 */
@RestController
@ExposesResourceFor(DestinationDTO.class)
@RequestMapping("/destinations")
public class DestinationsRestController {

    private FlightFacade flightFacade;
    private DestinationResourceAssembler destinationResourceAssembler;
    private DestinationFacade destinationFacade;
    private FlightResourceAssembler flightResourceAssembler;

    /**
     * Create DestinationsRestController
     * @param destinationFacade Destination facade.
     * @param destinationResourceAssembler Destination Resource Assembler.
     */
    public DestinationsRestController(
            @Autowired DestinationFacade destinationFacade,
            @Autowired FlightFacade flightFacade,
            @SuppressWarnings("SpringJavaAutowiringInspection")
            @Autowired DestinationResourceAssembler destinationResourceAssembler,
            @Autowired FlightResourceAssembler flightResourceAssembler)
     {
        this.destinationFacade = destinationFacade;
        this.destinationResourceAssembler = destinationResourceAssembler;
        this.flightFacade = flightFacade;
        this.flightResourceAssembler = flightResourceAssembler;
    }


    /**
     * Get all destinations.
     *
     * @return All destinations.
     */
    @RequestMapping(method = RequestMethod.GET)
    public final HttpEntity<Resources<DestinationResource>> destinations() {
        List<DestinationDTO> allDestinations = destinationFacade.getAllDestinations();
        Resources<DestinationResource> destinationResources = new Resources<>(
                destinationResourceAssembler.toResources(allDestinations),
                linkTo(DestinationsRestController.class).withSelfRel(),
                linkTo(DestinationsRestController.class).slash("/create").withRel("create"));
        return new ResponseEntity<>(destinationResources, HttpStatus.OK);
    }

    /**
     * Get destination by id.
     *
     * @param id Destination id
     * @return Destination.
     * @throws Exception if Destination not found.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public final HttpEntity<DestinationResource> destination(@PathVariable("id") long id) throws Exception {
        DestinationDTO destinationDTO = destinationFacade.getDestinationById(id);
        if (destinationDTO == null) throw new ResourceNotFoundException("Destination " + id + " not found");
        DestinationResource destinationResource = destinationResourceAssembler.toResource(destinationDTO);
        return new HttpEntity<>(destinationResource);
    }


    /* Update destination.
        * @param destinationDTO DTO object.
        * @return Updated destination.
        * @throws Exception if something goes wrong
        */
    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final void updateDestination(@PathVariable("id") long id,
                                    @RequestBody @Valid DestinationDTO destinationDTO,
                                    BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Failed validation");
        }
        if(id != destinationDTO.getId()) {
            throw new InvalidRequestException("Objects differ in ID");
        }
        destinationFacade.updateDestination(destinationDTO);
    }


     /**
     Creates a new destination.
     * @param destinationDTO DTO object.
     * @return newly created destination.
     * @throws Exception if something goes wrong
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<DestinationResource> createDestination(@RequestBody @Valid DestinationDTO destinationDTO,
                                                               BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Failed validation");
        }
        Long id = destinationFacade.createDestination(destinationDTO.getCountry(), destinationDTO.getCity());
        DestinationResource resource = destinationResourceAssembler.toResource(destinationFacade.getDestinationById(id));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    /**
     * Delete destination with id
     * @param id Id of destination.
     * @throws Exception If something went wrong.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public final void deleteDestination(@PathVariable("id") long id) throws Exception {
        try {
            destinationFacade.removeDestination(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Nothing to delete");
        }
    }

    /**
     * Get destinations by country.
     * @param country Destination country.
     * @return Destinations in given country.
     * @throws Exception If something went wrong.
     */
    @RequestMapping(value = "/country/{country}", method = RequestMethod.GET)
    public final HttpEntity<Resources<DestinationResource>> destinationByCountry(@PathVariable("country") String country) throws Exception {
        List<DestinationDTO> destinationDTOS = destinationFacade.getDestinationsByCountry(country);
        if (destinationDTOS == null) throw new ResourceNotFoundException("Destinations with country" + country + " not found");
        Resources<DestinationResource> destinationResources = new Resources<>(
                destinationResourceAssembler.toResources(destinationDTOS),
                linkTo(DestinationsRestController.class).withSelfRel(),
                linkTo(DestinationsRestController.class).slash("/create").withRel("create"));
        return new ResponseEntity<>(destinationResources, HttpStatus.OK);
    }

    /**
     * Get destinations by city.
     * @param city Destination city.
     * @return Destinations in given city.
     * @throws Exception If something went wrong.
     */
    @RequestMapping(value = "/city/{city}", method = RequestMethod.GET)
    public final HttpEntity<Resources<DestinationResource>> destinationByCity(@PathVariable("city") String city) throws Exception {
        List<DestinationDTO> destinationDTOS = destinationFacade.getDestinationsByCity(city);
        if (destinationDTOS == null) throw new ResourceNotFoundException("Destinations with city" + city + " not found");
        Resources<DestinationResource> destinationResources = new Resources<>(
                destinationResourceAssembler.toResources(destinationDTOS),
                linkTo(DestinationsRestController.class).withSelfRel(),
                linkTo(DestinationsRestController.class).slash("/create").withRel("create"));
        return new ResponseEntity<>(destinationResources, HttpStatus.OK);
    }

    /**
     * Get all incoming flight to destination.
     *
     * @param id Destination id.
     * @return All incoming flight to given destination.
     * @throws Exception If something went wrong.
     */
    @RequestMapping(value = "/{id}/incomingFlights", method = RequestMethod.GET)
    public final HttpEntity<Resources<FlightResource>> incomingFlights(@PathVariable("id") long id) throws Exception {
        DestinationDTO destinationById = destinationFacade.getDestinationById(id);
        if (destinationById == null) throw new ResourceNotFoundException("Destinations with id" + id + " not found");
        List<FlightResource> resourcesCollection = flightResourceAssembler.toResources(destinationFacade.getAllIncomingFlights(destinationById));
        Resources<FlightResource> flightResources = new Resources<>(resourcesCollection);
        return new ResponseEntity<>(flightResources, HttpStatus.OK);
    }

    /**
     * Get all outgoing flight to destination.
     *
     * @param id Destination id.
     * @return All outgoing flight to given destination.
     * @throws Exception If something went wrong.
     */
    @RequestMapping(value = "/{id}/outgoingFlights", method = RequestMethod.GET)
    public final HttpEntity<Resources<FlightResource>> outgoingFlights (@PathVariable("id") long id) throws Exception {
        DestinationDTO destinationById = destinationFacade.getDestinationById(id);
        if (destinationById == null) throw new ResourceNotFoundException("Destinations with id" + id + " not found");
        List<FlightResource> resourcesCollection = flightResourceAssembler.toResources(destinationFacade.getAllOutgoingFlights(destinationById));
        Resources<FlightResource> flightResources = new Resources<>(resourcesCollection);
        return new ResponseEntity<>(flightResources, HttpStatus.OK);
    }







}
