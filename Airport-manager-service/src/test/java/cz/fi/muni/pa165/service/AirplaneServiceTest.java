package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dao.AirplaneDao;
import cz.fi.muni.pa165.entities.Airplane;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Karel Jiranek
 */
public class AirplaneServiceTest extends BaseServiceTest {
    @Mock
    private AirplaneDao airplaneDao;

    @Autowired
    @InjectMocks
    private AirplaneService airplaneService;

    private Airplane airplaneA;

    private Airplane airplaneB;


    @BeforeMethod
    public void prepareAirplane(){
        airplaneA = new Airplane();
        airplaneA.setId(5L);
        airplaneA.setType("Large");
        airplaneA.setName("Boeing 747");
        airplaneA.setCapacity(400);

        airplaneB = new Airplane();
        airplaneB.setId(4L);
        airplaneB.setType("Small");
        airplaneB.setName("Cesna");
        airplaneB.setCapacity(10);
    }

    @Test
    public void findAllTest(){
        when(airplaneDao.findAll()).thenReturn(Arrays.asList(airplaneA, airplaneB));
        List<Airplane> allFlights = airplaneService.findAll();

        Assert.assertEquals(allFlights.size(), 2);
        Assert.assertTrue(allFlights.get(0).getId().equals(5L) || allFlights.get(0).getId().equals(4L));
    }

    @Test
    public void findByNameTest(){
        when(airplaneDao.findByName("Boeing 747")).thenReturn(Collections.singletonList(airplaneA));
        List<Airplane> allFlights = airplaneService.findByName("Boeing 747");

        Assert.assertEquals(allFlights.size(), 1);
        Assert.assertTrue(allFlights.get(0).getId().equals(5L));
    }

    @Test
    public void findFindByType(){
        when(airplaneDao.findByType("Small")).thenReturn(Collections.singletonList(airplaneB));
        List<Airplane> allFlights = airplaneService.findByType("Small");

        Assert.assertEquals(allFlights.size(), 1);
        Assert.assertTrue(allFlights.get(0).getId().equals(4L));

        verify(airplaneDao).findByType("Small");
    }


}
