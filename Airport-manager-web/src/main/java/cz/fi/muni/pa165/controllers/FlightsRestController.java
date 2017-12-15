package cz.fi.muni.pa165.controllers;

import cz.fi.muni.pa165.dto.FlightDTO;
import cz.fi.muni.pa165.exceptions.ResourceNotFoundException;
import cz.fi.muni.pa165.facade.FlightFacade;
import cz.fi.muni.pa165.hateoas.FlightResource;
import cz.fi.muni.pa165.hateoas.FlightResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

}
