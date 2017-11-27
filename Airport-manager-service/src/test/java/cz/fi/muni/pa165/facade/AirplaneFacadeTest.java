package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.AirplaneDTO;
import cz.fi.muni.pa165.entities.Airplane;
import cz.fi.muni.pa165.service.AirplaneService;
import cz.fi.muni.pa165.service.MappingService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Robert Duriancik
 */
public class AirplaneFacadeTest extends BaseFacadeTest {

    @Mock
    private AirplaneService airplaneService;
    @Mock
    private MappingService mappingService;

    @Autowired
    @InjectMocks
    private AirplaneFacadeImpl airplaneFacade;

    private Airplane airplane;
    private AirplaneDTO airplaneDTO;
    private Long airplaneId;
    private String airplaneName;
    private String airplaneType;
    private int airplaneCapacity;

    @BeforeMethod
    public void prepareTestData() {
        airplaneId = 1L;
        airplaneName = "Nunez";
        airplaneType = "Rachotina";
        airplaneCapacity = 100;

        airplane = new Airplane();
        airplane.setId(airplaneId);
        airplane.setName(airplaneName);
        airplane.setType(airplaneType);
        airplane.setCapacity(airplaneCapacity);

        airplaneDTO = new AirplaneDTO();
        airplaneDTO.setId(airplaneId);
        airplaneDTO.setName(airplaneName);
        airplaneDTO.setType(airplaneType);
        airplaneDTO.setCapacity(airplaneCapacity);

        Mockito.reset(airplaneService, mappingService);
    }

    @Test
    public void testFindById() throws Exception {
        when(airplaneService.findById(airplaneId)).thenReturn(airplane);
        when(mappingService.mapTo(airplane, AirplaneDTO.class)).thenReturn(airplaneDTO);

        AirplaneDTO returnedAirplaneDTO = airplaneFacade.findById(airplaneId);

        Assert.assertEquals(returnedAirplaneDTO, airplaneDTO);
        verify(airplaneService).findById(airplaneId);
    }

    @Test
    public void testAddAirplane() throws Exception {
        when(mappingService.mapTo(airplaneDTO, Airplane.class)).thenReturn(airplane);

        Long returnedId = airplaneFacade.addAirplane(airplaneDTO);

        Assert.assertEquals(returnedId, airplaneId);
        verify(airplaneService).addAirplane(airplane);
    }

    @Test
    public void testFindAll() throws Exception {
        when(airplaneService.findAll())
                .thenReturn(Collections.singletonList(airplane));
        when(mappingService.mapTo(Collections.singletonList(airplane), AirplaneDTO.class))
                .thenReturn(Collections.singletonList(airplaneDTO));

        List<AirplaneDTO> returnedAirplaneDTOS = airplaneFacade.findAll();

        Assert.assertEquals(returnedAirplaneDTOS, Collections.singletonList(airplaneDTO));
        verify(airplaneService).findAll();
    }

    @Test
    public void testDeleteAirplane() throws Exception {
        when(airplaneService.findById(airplaneId)).thenReturn(airplane);

        airplaneFacade.deleteAirplane(airplaneId);

        verify(airplaneService).findById(airplaneId);
        verify(airplaneService).deleteAirplane(airplane);
    }

    @Test
    public void testUpdateAirplane() throws Exception {
        when(mappingService.mapTo(airplaneDTO, Airplane.class)).thenReturn(airplane);

        airplaneFacade.updateAirplane(airplaneDTO);

        verify(airplaneService).updateAirplane(airplane);
    }

    @Test
    public void testFindByName() throws Exception {
        when(airplaneService.findByName(airplaneName))
                .thenReturn(Collections.singletonList(airplane));
        when(mappingService.mapTo(Collections.singletonList(airplane), AirplaneDTO.class))
                .thenReturn(Collections.singletonList(airplaneDTO));

        List<AirplaneDTO> returnedAirplaneDTOS = airplaneFacade.findByName(airplaneName);

        Assert.assertEquals(returnedAirplaneDTOS, Collections.singletonList(airplaneDTO));
        verify(airplaneService).findByName(airplaneName);
    }

    @Test
    public void testFindByType() throws Exception {
        when(airplaneService.findByType(airplaneType))
                .thenReturn(Collections.singletonList(airplane));
        when(mappingService.mapTo(Collections.singletonList(airplane), AirplaneDTO.class))
                .thenReturn(Collections.singletonList(airplaneDTO));

        List<AirplaneDTO> returnedAirplaneDTOS = airplaneFacade.findByType(airplaneType);

        Assert.assertEquals(returnedAirplaneDTOS, Collections.singletonList(airplaneDTO));
        verify(airplaneService).findByType(airplaneType);
    }

    @Test
    public void testFindByCapacityMin() throws Exception {
        when(airplaneService.findByCapacityMin(airplaneCapacity))
                .thenReturn(Collections.singletonList(airplane));
        when(mappingService.mapTo(Collections.singletonList(airplane), AirplaneDTO.class))
                .thenReturn(Collections.singletonList(airplaneDTO));

        List<AirplaneDTO> returnedAirplaneDTOS = airplaneFacade.findByCapacityMin(airplaneCapacity);

        Assert.assertEquals(returnedAirplaneDTOS, Collections.singletonList(airplaneDTO));
        verify(airplaneService).findByCapacityMin(airplaneCapacity);
    }

    @Test
    public void testFindByCapacityMax() throws Exception {
        when(airplaneService.findByCapacityMax(airplaneCapacity))
                .thenReturn(Collections.singletonList(airplane));
        when(mappingService.mapTo(Collections.singletonList(airplane), AirplaneDTO.class))
                .thenReturn(Collections.singletonList(airplaneDTO));

        List<AirplaneDTO> returnedAirplaneDTOS = airplaneFacade.findByCapacityMax(airplaneCapacity);

        Assert.assertEquals(returnedAirplaneDTOS, Collections.singletonList(airplaneDTO));
        verify(airplaneService).findByCapacityMax(airplaneCapacity);
    }

    @Test
    public void testFindByUsedAfterDateTime() throws Exception {
        LocalDateTime testTime = LocalDateTime.now();
        when(airplaneService.findByUsedAfterDateTime(testTime))
                .thenReturn(Collections.singletonList(airplane));
        when(mappingService.mapTo(Collections.singletonList(airplane), AirplaneDTO.class))
                .thenReturn(Collections.singletonList(airplaneDTO));

        List<AirplaneDTO> returnedAirplaneDTOS = airplaneFacade.findByUsedAfterDateTime(testTime);

        Assert.assertEquals(returnedAirplaneDTOS, Collections.singletonList(airplaneDTO));
        verify(airplaneService).findByUsedAfterDateTime(testTime);
    }

    @Test
    public void testFindByFreeAfterDateTime() throws Exception {
        LocalDateTime testTime = LocalDateTime.now();
        when(airplaneService.findByFreeAfterDateTime(testTime))
                .thenReturn(Collections.singletonList(airplane));
        when(mappingService.mapTo(Collections.singletonList(airplane), AirplaneDTO.class))
                .thenReturn(Collections.singletonList(airplaneDTO));

        List<AirplaneDTO> returnedAirplaneDTOS = airplaneFacade.findByFreeAfterDateTime(testTime);

        Assert.assertEquals(returnedAirplaneDTOS, Collections.singletonList(airplaneDTO));
        verify(airplaneService).findByFreeAfterDateTime(testTime);
    }

}