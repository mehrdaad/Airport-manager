package cz.fi.muni.pa165.controllers;

import cz.fi.muni.pa165.dto.FlightCreateDTO;
import cz.fi.muni.pa165.dto.FlightDTO;
import cz.fi.muni.pa165.exceptions.FlightDataAccessException;
import cz.fi.muni.pa165.exceptions.InvalidRequestException;
import cz.fi.muni.pa165.exceptions.ResourceNotFoundException;
import cz.fi.muni.pa165.facade.FlightFacade;
import cz.fi.muni.pa165.hateoas.FlightResource;
import cz.fi.muni.pa165.hateoas.FlightResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
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


@RestController
@RequestMapping("/flights")
public class FlightsRestController {

    private FlightFacade flightFacade;
    private FlightResourceAssembler flightResourceAssembler;


    public FlightsRestController(
            @Autowired FlightFacade flightFacade,
            @Autowired FlightResourceAssembler flightResourceAssembler
    ) {

        this.flightFacade = flightFacade;
        this.flightResourceAssembler = flightResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.GET)
    public final HttpEntity<Resources<FlightResource>> getFlights() {
        List<FlightResource> resourcesCollection = flightResourceAssembler.toResources(flightFacade.getAllFlights());
        Resources<FlightResource> flightResources = new Resources<>(resourcesCollection,
                linkTo(FlightsRestController.class).withSelfRel(),
                linkTo(FlightsRestController.class).slash("/create").withRel("create"));
        return new ResponseEntity<>(flightResources, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public final HttpEntity<FlightResource> getFlight(@PathVariable("id") long id) throws Exception {
        FlightDTO flightDTO = flightFacade.getFlight(id);
        if (flightDTO == null) throw new ResourceNotFoundException("Flight " + id + " not found.");
        FlightResource flightResource = flightResourceAssembler.toResource(flightDTO);
        return new ResponseEntity<>(flightResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public final void deleteFlight(@PathVariable("id") long id) throws Exception {
        try {
            flightFacade.deleteFlight(id);
        } catch (FlightDataAccessException e) {
            // TODO error handling
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<FlightResource> createFlight(@RequestBody @Valid FlightCreateDTO flightCreateDTO,
                                                         BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Failed validation");
        }

        Long id = flightFacade.createFlight(flightCreateDTO);
        FlightResource flightResource = flightResourceAssembler.toResource(flightFacade.getFlight(id));
        return new ResponseEntity<>(flightResource, HttpStatus.OK);
    }
}
