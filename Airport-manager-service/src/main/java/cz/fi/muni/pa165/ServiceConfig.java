package cz.fi.muni.pa165;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(PersistenceConfig.class)
@ComponentScan("cz.fi.muni.pa165")
public class ServiceConfig {
    @Bean
    public Mapper dozer() {
        return new DozerBeanMapper();
    }
}
