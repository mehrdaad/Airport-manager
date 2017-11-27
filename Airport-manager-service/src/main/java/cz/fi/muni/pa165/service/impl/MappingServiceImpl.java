package cz.fi.muni.pa165.service.impl;

import cz.fi.muni.pa165.service.MappingService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Karel Jiranek
 */
@Service
public class MappingServiceImpl implements MappingService {

    @Autowired
    private Mapper dozer;

    /**
     * Map objects of any type to given class.
     *
     * @param objects Objects to be mapped.
     * @param mapToClass Class to be mapped to.
     * @param <T> Type of class.
     * @return List of mapped objects.
     */
    public  <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass) {
        List<T> mappedCollection = new ArrayList<>();
        for (Object object : objects) {
            mappedCollection.add(dozer.map(object, mapToClass));
        }
        return mappedCollection;
    }

    /**
     * Map given object to given class.
     *
     * @param object Object to be mapped.
     * @param mapToClass Class to be mapped to.
     * @param <T> Type of class.
     * @return List of mapped objects.
     */
    public  <T> T mapTo(Object object, Class<T> mapToClass)
    {
        return dozer.map(object,mapToClass);
    }

    /**
     * Get mapper.
     *
     * @return Mapper
     */
    public Mapper getMapper(){
        return dozer;
    }
}
