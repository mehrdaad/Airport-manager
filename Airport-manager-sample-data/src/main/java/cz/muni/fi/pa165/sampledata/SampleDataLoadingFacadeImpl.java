package cz.muni.fi.pa165.sampledata;

import cz.fi.muni.pa165.service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Karel Jiranek
 */
@Component
@Transactional
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {

    @Autowired
    private DestinationService  destinationService;

    @Override
    @SuppressWarnings("unused")
    public void loadData() {
        destinationService.createDestination("US", "Boston");
    }
}
