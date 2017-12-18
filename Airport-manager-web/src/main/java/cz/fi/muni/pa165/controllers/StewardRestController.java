package cz.fi.muni.pa165.controllers;

import cz.fi.muni.pa165.dto.StewardCreateDTO;
import cz.fi.muni.pa165.dto.StewardDTO;
import cz.fi.muni.pa165.exceptions.InvalidRequestException;
import cz.fi.muni.pa165.exceptions.ResourceNotFoundException;
import cz.fi.muni.pa165.exceptions.StewardDataAccessException;
import cz.fi.muni.pa165.facade.StewardFacade;
import cz.fi.muni.pa165.hateoas.FlightResource;
import cz.fi.muni.pa165.hateoas.FlightResourceAssembler;
import cz.fi.muni.pa165.hateoas.StewardResource;
import cz.fi.muni.pa165.hateoas.StewardResourceAssembler;
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

/**
 * @author Ondrej Prikryl
 */
@RestController
@RequestMapping("/stewards")
public class StewardRestController {

    private StewardFacade stewardFacade;
    private StewardResourceAssembler assembler;
    private FlightResourceAssembler flightResourceAssembler;

    public StewardRestController(@Autowired StewardFacade flightFacade,
                                 @Autowired StewardResourceAssembler stewardResourceAssembler,
                                 @Autowired FlightResourceAssembler flightResourceAssembler) {

        this.stewardFacade = flightFacade;
        this.assembler = stewardResourceAssembler;
        this.flightResourceAssembler = flightResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.GET)
    public final HttpEntity<Resources<StewardResource>> getStewards() {
        List<StewardResource> resourcesCollection = assembler.toResources(stewardFacade.listAllStewards());
        Resources<StewardResource> stewardResources = new Resources<>(resourcesCollection,
                linkTo(StewardRestController.class).withSelfRel(),
                linkTo(StewardRestController.class).slash("/create").withRel("create"));
        return new ResponseEntity<>(stewardResources, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public final HttpEntity<StewardResource> getSteward(@PathVariable("id") long id) throws Exception {
        StewardDTO stewardDTO = stewardFacade.getSteward(id);
        if (stewardDTO == null) throw new ResourceNotFoundException("Steward " + id + " not found.");
        StewardResource resource = assembler.toResource(stewardDTO);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/flights", method = RequestMethod.GET)
    public final ResponseEntity<Resources<FlightResource>> getStewardFlights(@PathVariable("id") long id) throws Exception {
        List<FlightResource> resourcesCollection = flightResourceAssembler.toResources(stewardFacade.getAllStewardFlights(id));
        Resources<FlightResource> flightResources = new Resources<>(resourcesCollection);
        return new ResponseEntity<>(flightResources, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public final void deleteSteward(@PathVariable("id") long id) throws Exception {
        try {
            System.out.println("Id is: " + id);
            stewardFacade.deleteSteward(id);
        } catch (StewardDataAccessException e) {
            System.out.println("Exception: " + e);
            e.printStackTrace();
            // TODO error handling
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
        public final HttpEntity<StewardResource> createSteward(@RequestBody @Valid StewardCreateDTO stewardCreateDTO,
                                                               BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Failed validation");
        }

        Long id = stewardFacade.createSteward(stewardCreateDTO.getFirstname(), stewardCreateDTO.getSurname());
        StewardResource stewardResource = assembler.toResource(stewardFacade.getSteward(id));
        return new ResponseEntity<>(stewardResource, HttpStatus.OK);
    }
}
