package cz.fi.muni.pa165.exceptions;

import org.springframework.dao.DataAccessException;
/**
 *
 * @author Jan Cakl
 */
public class AirplaneDataAccessException extends DataAccessException{
    
    public AirplaneDataAccessException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
