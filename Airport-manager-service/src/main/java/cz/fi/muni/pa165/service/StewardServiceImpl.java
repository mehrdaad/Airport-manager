package cz.fi.muni.pa165.service;


import cz.fi.muni.pa165.dao.DestinationDao;
import cz.fi.muni.pa165.dao.FlightDao;
import cz.fi.muni.pa165.dao.StewardDao;
import cz.fi.muni.pa165.entities.Destination;
import cz.fi.muni.pa165.entities.Flight;
import cz.fi.muni.pa165.entities.Steward;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link StewardService}. This class is part of the service
 * module of the application that provides the implementation of the business
 * logic.
 *
 * @author Karel Jiranek
 */
@Service
public class StewardServiceImpl implements StewardService{
    @Autowired
    private StewardDao stewardDao;

    @Autowired
    private FlightDao flightDao;

    @Autowired
    private DestinationDao destinationDao;


    /**
     * Get steward by id.
     * @param id Id identifying steward.
     * @return Steward with given id.
     */
    @Override
    public Steward findStewardById(Long id) {
        return stewardDao.getSteward(id);
    }

    /**
     * This method returns all stewards stored in the database.
     *
     * @return List of all stewards currently stored in database.
     */
    @Override
    public List<Steward> listAllStewards(){
        return stewardDao.listAllStewards();
    }

    /**
     * Delete steward
     *
     * @param steward Steward to delete.
      */
    @Override
    public void deleteSteward(Steward steward){
        stewardDao.deleteSteward(steward);
    }

    /**
     * Get all stewards full names ordered by surname/first name.
     * Surname is first in full name.
     *
     * @return Ordered list of stewards full names.
     */
    @Override
    public List<String> getAllStewardsNameOrdered(){
        List<String> stewardsFullNames = stewardDao.listAllStewards()
                .stream()
                .map(steward -> steward.getSurname() + " " + steward.getFirstName())
                .sorted()
                .collect(Collectors.toList());

        return stewardsFullNames;
    }

    /**
     * Create steward with name and surname.
     *
     * @param firstName Steward first name.
     * @param surName Steward surname.
     */
    @Override
    public void createSteward(String firstName, String surName){
        Steward steward = new Steward();
        steward.setFirstName(firstName);
        steward.setSurname(surName);
        stewardDao.createSteward(steward);
    }

    /**
     * Update steward name (first name and surname).
     *
     * @param id Steward's id
     * @param firstName New first name.
     * @param surName New surname.
     */
    @Override
    public void updateStewardName(long id, String firstName, String surName){
        Steward steward = stewardDao.getSteward(id);

        steward.setFirstName(firstName);
        steward.setSurname(surName);
        stewardDao.updateSteward(steward);
    }

    /**
     * Check if stewards is in the air. If stewards is departured flight and
     * airplane is still or already on the land it is considered as in the air.
     *
     * @param id Steward id.
     * @return True if stewards is in the air (serving flight).
     */
    @Override
    public boolean isStewardInTheAir(long id){
        return getCurrentStewardFlight(id) == null ? false : true;
    }

    /**
     * Get all flights that given steward served, serving and going to serve.
     *
     * @param id Steward id.
     * @return All flights that given steward served. Empty list if not any.
     */
    @Override
    public List<Flight> getAllStewardsFlights(long id){
        return getAllStewardsFlightsAfter(id, LocalDateTime.MIN);
    }

    /**
     * Get all flights that given steward going to serve in future. Only not departured flights are
     * included in result.
     *
     * @param id Steward id.
     * @return All flights that given steward going to serve in future. Empty list if not any.
     */
    @Override
    public List<Flight> getAllStewardsFutureFlights(long id){
        return getAllStewardsFlightsAfter(id, LocalDateTime.now());
    }

    /**
     * Get all stewards departured flights after given time.
     *
     * @param id Steward id.
     * @param startTime Time to start searching from.
     * @return All stewards flights after given time (date). Empty list if not any.
     */
    @Override
    public List<Flight> getAllStewardsFlightsAfter(long id, LocalDateTime startTime){
        return getAllStewardsFlightsInTimeRange(id, startTime, LocalDateTime.MAX);
    }

    /**
     * Get all stewards departured flights before given time. Flight in the air is
     * included if departured before give time.
     *
     * @param id Steward id.
     * @param stopTime Time to stop searching.
     * @return All stewards flights before given time (date). Empty list if not any.
     */
    @Override
    public List<Flight> getAllStewardsFlightsBefore(long id, LocalDateTime stopTime) {
        return getAllStewardsFlightsInTimeRange(id, LocalDateTime.MIN, stopTime);
    }

    /**
     * Get all stewards flights in given time range. All departured flights in range are
     * included in result.
     *
     * @param id Steward id.
     * @param startTime Time to start searching from.
     * @param stopTime Time to stop searching.
     * @return All stewards flights in given time range. Empty list if not any.
     */
    @Override
    public List<Flight> getAllStewardsFlightsInTimeRange(long id, LocalDateTime startTime, LocalDateTime stopTime){
        Steward steward = stewardDao.getSteward(id);
        List<Flight> allStewardFlightsAfter = new ArrayList<>();

        for (Flight flight: flightDao.getAllFlight()){
            List<Steward> flightStewards = flight.getStewards();
            LocalDateTime departureTime = flight.getDepartureTime();
            if (flightStewards.contains(steward) &&
                    departureTime.isAfter(startTime) &&
                    departureTime.isBefore(stopTime)){
                allStewardFlightsAfter.add(flight);
            }
        }
        return allStewardFlightsAfter;
    }

    /**
     * Get the last stewards flight. If the steward is in the air that flight is considered as the last flight.
     *
     * @param id Steward id.
     * @return Last stewards flight. Null if no flight found.
     */
    @Override
    public Flight getStewardsLastFlight(long id){
        Steward steward = stewardDao.getSteward(id);
        List<Flight> allFlights = flightDao.getAllFlight();

        // No flights found
        if (allFlights == null){
            return null;
        }

        // Init last flight
        Flight lastFlight = allFlights.get(0);

        // Find last flight
        for (Flight flight: allFlights){
            List<Steward> flightStewards = flight.getStewards();
            LocalDateTime flightDepartureTime = flight.getDepartureTime();
            if (flightStewards.contains(steward) &&
                    flightDepartureTime.isBefore(LocalDateTime.now()) &&
                    flightDepartureTime.isAfter(lastFlight.getDepartureTime())){
                lastFlight = flight;
            }
        }
        return lastFlight;
    }

    /**
     * Get flight the steward is currently serving. Steward is in the air.
     *
     * @param id Steward id.
     * @return Flight the steward is currently serving. Null if stewards is not in the air.
     */
    @Override
    public Flight getCurrentStewardFlight(long id){
        Steward steward = stewardDao.getSteward(id);

        for (Flight flight: flightDao.getAllFlight()){
            List<Steward> flightStewards = flight.getStewards();
            if (flightStewards.contains(steward) &&
                    flight.getDepartureTime().isBefore(LocalDateTime.now()) &&
                    flight.getArrivalTime().isAfter(LocalDateTime.now())){
                return flight;
            }
        }
        return null;
    }





}
