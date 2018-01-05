package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.FlightDTO;
import cz.fi.muni.pa165.dto.StewardDTO;
import cz.fi.muni.pa165.entities.Airplane;
import cz.fi.muni.pa165.entities.Destination;
import cz.fi.muni.pa165.entities.Flight;
import cz.fi.muni.pa165.entities.Steward;
import cz.fi.muni.pa165.service.MappingService;
import cz.fi.muni.pa165.service.StewardService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Jan Cakl
 */

public class StewardFacadeTest extends BaseFacadeTest {

    @Mock
    private StewardService stewardService;

    @Mock
    private MappingService mappingService;

    private Airplane airplane;

    private LocalDateTime departureTime_past;
    private LocalDateTime arrivalTime_past;

    @Mock
    private Steward steward;

    @Mock
    StewardDTO stewardDTO;

    @Mock
    FlightDTO flightDTO;

    @Autowired
    @InjectMocks
    private StewardFacadeImpl stewardFacade;

    @BeforeMethod
    public void prepareTestData() {
        steward = new Steward();
        steward.setId(1L);
        steward.setFirstName("Name");
        steward.setSurname("Surname");

        stewardDTO = new StewardDTO();
        stewardDTO.setId(1L);
        steward.setFirstName("Name");
        steward.setSurname("Surname");

        Mockito.reset(stewardService, mappingService);
    }

    @Test
    public void testCreateSteward() {
        when(mappingService.mapTo(stewardDTO, Steward.class)).thenReturn(steward);
        stewardFacade.createSteward("TestName", "TestSurname");
        verify(stewardService).createSteward("TestName", "TestSurname");
    }

    @Test
    public void testGetSteward() {
        when(stewardService.getSteward(steward.getId())).thenReturn(steward);
        when(mappingService.mapTo(steward, StewardDTO.class)).thenReturn(stewardDTO);
        StewardDTO tmp = stewardFacade.getSteward(steward.getId());

        verify(stewardService).getSteward(steward.getId());
        Assert.assertTrue(stewardDTO.equals(tmp));
    }

    @Test
    public void testDeleteSteward() {
        when(stewardService.getSteward(steward.getId())).thenReturn(steward);
        stewardFacade.deleteSteward(steward.getId());

        verify(stewardService).deleteSteward(steward);
    }

    @Test
    public void testUpdateSteward() throws Exception {
        when(mappingService.mapTo(stewardDTO, Steward.class)).thenReturn(steward);

        stewardFacade.updateSteward(stewardDTO);

        verify(stewardService).updateSteward(steward);
    }

    @Test
    public void testListAllStewards() throws Exception {
        when(stewardService.getAllStewardsNameOrdered()).thenReturn(Collections.singletonList(steward));
        when(mappingService.mapTo(Collections.singletonList(steward), StewardDTO.class)).thenReturn(Collections.singletonList(stewardDTO));

        List<StewardDTO> returnedStewardDTOS = stewardFacade.listAllStewards();

        Assert.assertEquals(returnedStewardDTOS, Collections.singletonList(stewardDTO));
        verify(stewardService).getAllStewardsNameOrdered();
    }

    @Test
    public void testFindAll() throws Exception {
        when(stewardService.getAllStewardsNameOrdered()).thenReturn(Collections.singletonList(steward));
        when(mappingService.mapTo(Collections.singletonList(steward), StewardDTO.class)).thenReturn(Collections.singletonList(stewardDTO));

        List<StewardDTO> returnedStewardDTOS = stewardFacade.listAllStewards();

        Assert.assertEquals(returnedStewardDTOS, Collections.singletonList(stewardDTO));
        verify(stewardService).getAllStewardsNameOrdered();
    }

    @Test
    public void testGetAllStewardsFlightsInTimeRange() throws Exception {

        LocalDateTime startTime = LocalDateTime.of(2019, Month.NOVEMBER, 19, 8, 30);
        LocalDateTime stopTime = LocalDateTime.of(2026, Month.NOVEMBER, 26, 20, 30);


        airplane = new Airplane();
        airplane.setId(1L);
        airplane.setCapacity(10);
        airplane.setName("Airplane1");
        airplane.setType("AK-447");

        departureTime_past = LocalDateTime.of(2000, Month.NOVEMBER, 20, 8, 30);
        arrivalTime_past = LocalDateTime.of(2030, Month.NOVEMBER, 25, 20, 30);

        Flight flight1 = new Flight();
        flight1.setAirplane(airplane);
        flight1.setDepartureTime(departureTime_past);
        flight1.setArrivalTime(arrivalTime_past);
        flight1.setDepartureLocation(new Destination());
        flight1.addSteward(steward);

        when(stewardService.getAllStewardFlightsInTimeRange(1L, startTime, stopTime)).thenReturn(Collections.singletonList(flight1));
        when(mappingService.mapTo(Collections.singletonList(flight1), FlightDTO.class)).thenReturn(Collections.singletonList(flightDTO));

        List<FlightDTO> returnedFlightDTOS = stewardFacade.getAllStewardsFlightsInTimeRange(1L, startTime, stopTime);

        Assert.assertEquals(returnedFlightDTOS, Collections.singletonList(flightDTO));
        verify(stewardService).getAllStewardFlightsInTimeRange(1L, startTime, stopTime);
    }

    @Test
    public void testGetAllStewardFlights() throws Exception {

        airplane = new Airplane();
        airplane.setId(1L);
        airplane.setCapacity(10);
        airplane.setName("Airplane1");
        airplane.setType("AK-447");

        departureTime_past = LocalDateTime.of(2000, Month.NOVEMBER, 20, 8, 30);
        arrivalTime_past = LocalDateTime.of(2030, Month.NOVEMBER, 25, 20, 30);

        Flight flight1 = new Flight();
        flight1.setAirplane(airplane);
        flight1.setDepartureTime(departureTime_past);
        flight1.setArrivalTime(arrivalTime_past);
        flight1.setDepartureLocation(new Destination());
        flight1.addSteward(steward);

        when(stewardService.getAllStewardFlights(1L)).thenReturn(Collections.singletonList(flight1));
        when(mappingService.mapTo(Collections.singletonList(flight1), FlightDTO.class)).thenReturn(Collections.singletonList(flightDTO));

        List<FlightDTO> returnedFlightDTOS = stewardFacade.getAllStewardFlights(1L);

        Assert.assertEquals(returnedFlightDTOS, Collections.singletonList(flightDTO));
        verify(stewardService).getAllStewardFlights(1L);
    }

    @Test
    public void testGetFreeStewardsInTimeRange() throws Exception {
        departureTime_past = LocalDateTime.of(2000, Month.NOVEMBER, 20, 8, 30);
        arrivalTime_past = LocalDateTime.of(2030, Month.NOVEMBER, 25, 20, 30);

        when(stewardService.getFreeStewardsInTimeRange(departureTime_past, arrivalTime_past)).thenReturn(Collections.singletonList(steward));
        when(mappingService.mapTo(Collections.singletonList(steward), StewardDTO.class)).thenReturn(Collections.singletonList(stewardDTO));

        List<StewardDTO> returnedStewards = stewardFacade.getFreeStewardsInTimeRange(departureTime_past, arrivalTime_past);

        Assert.assertEquals(returnedStewards.size(), 1);
        Assert.assertEquals(returnedStewards.get(0), stewardDTO);
    }
}
