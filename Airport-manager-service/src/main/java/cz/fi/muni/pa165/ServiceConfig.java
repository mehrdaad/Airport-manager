package cz.fi.muni.pa165;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.Collections;

@Configuration
@Import(PersistenceConfig.class)
@ComponentScan("cz.fi.muni.pa165")
public class ServiceConfig {

    /**
     * Create dozer.
     * @return New bean dozer.
     */
    @Bean
    public Mapper dozer() {
        DozerBeanMapper mapper = new DozerBeanMapper();
        mapper.setMappingFiles(Collections.singletonList("dozerJdk8Converters.xml")); // enables LocalDateTime mapping
        return mapper;
    }
}
