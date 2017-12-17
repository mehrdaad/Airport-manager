package cz.fi.muni.pa165.service.impl;


import cz.fi.muni.pa165.dao.FlightDao;
import cz.fi.muni.pa165.dao.StewardDao;
import cz.fi.muni.pa165.entities.Flight;
import cz.fi.muni.pa165.entities.Steward;
import cz.fi.muni.pa165.exceptions.StewardDataAccessException;
import cz.fi.muni.pa165.service.StewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    /**
     * Get steward by id.
     * @param id Id identifying steward.
     * @return Steward with given id.
     */
    @Override
    public Steward getSteward(Long id) {
        return stewardDao.getSteward(id);
    }

    /**
     * Delete steward
     *
     * @param steward Steward to delete.
      */
    @Override
    public void deleteSteward(Steward steward){
        try {
            stewardDao.deleteSteward(steward);
        }catch (Exception e) {
            throw new StewardDataAccessException("Exception while deleting steward: " + steward, e);
        }
    }

    /**
     * Get all stewards full names ordered by surname/first name.
     * Surname is first in full name.
     *
     * @return Ordered list of stewards full names.
     */
    @Override
    public List<Steward> getAllStewardsNameOrdered(){
        List<Steward> stewards = stewardDao.listAllStewards();
        Collections.sort(stewards, (s1, s2) -> {
            int surNameEquality = s1.getSurname().compareTo(s2.getSurname());
            if(surNameEquality == 0){
                return s1.getFirstName().compareTo(s2.getFirstName());
            }
            return surNameEquality;
        });
        return stewards;
    }

    /**
     * Create steward with name and surname.
     *
     * @param firstName Steward first name.
     * @param surName Steward surname.
     */
    @Override
    public Long createSteward(String firstName, String surName){
        try{
        Steward steward = new Steward();
        steward.setFirstName(firstName);
        steward.setSurname(surName);
        stewardDao.createSteward(steward);
        return steward.getId();
        }catch (Exception e) {
            throw new StewardDataAccessException("Exception while deleting steward.", e);
        }
    }

    /**
     * Update steward name (first name and surname).
     *
     * @param steward Steward to update.
     */
    @Override
    public void updateSteward(Steward steward){
        try {
            stewardDao.updateSteward(steward);
        }catch (Exception e) {
            throw new StewardDataAccessException("Exception while deleting steward: " + steward, e);
        }
    }

    /**
     * Get all flights that given steward served, serving and going to serve.
     *
     * @param id Steward id.
     * @return All flights that given steward served. Empty list if not any.
     */
    @Override
    public List<Flight> getAllStewardFlights(long id){
        return getAllStewardFlightsInTimeRange(id, LocalDateTime.MIN, LocalDateTime.MAX);
    }

    /**
     * Get first future flight that given steward going to serve in future.
     *
     * @param id Steward id.
     * @return First future flight that given steward going to serve in future.
     */
    @Override
    public Flight getStewardFutureFlight(long id){
        Steward steward = stewardDao.getSteward(id);
        List<Flight> allFlights = flightDao.getAllFlights();

        // No flights found
        if (allFlights == null){
            return null;
        }

        // Init last flight
        Flight firstFutureFlight = allFlights.get(0);

        // Find first future flight
        for (Flight flight: allFlights){
            List<Steward> flightStewards = flight.getStewards();
            LocalDateTime flightDepartureTime = flight.getDepartureTime();
            if (flightStewards.contains(steward) &&
                    flightDepartureTime.isBefore(firstFutureFlight.getDepartureTime()) &&
                    flightDepartureTime.isAfter(LocalDateTime.now())){
                firstFutureFlight = flight;
            }
        }
        return firstFutureFlight;
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
    public List<Flight> getAllStewardFlightsInTimeRange(long id, LocalDateTime startTime, LocalDateTime stopTime){
        Steward steward = stewardDao.getSteward(id);
        List<Flight> allStewardFlightsAfter = new ArrayList<>();

        for (Flight flight : flightDao.getAllFlights()) {
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
    public Flight getStewardLastFlight(long id){
        Steward steward = stewardDao.getSteward(id);
        List<Flight> allFlights = flightDao.getAllFlights();

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
    public Flight getStewardCurrentFlight(long id){
        Steward steward = stewardDao.getSteward(id);

        for (Flight flight : flightDao.getAllFlights()) {
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
