package cz.fi.muni.pa165.exceptions;

import org.springframework.dao.DataAccessException;
/**
 *
 * @author Jan Cakl
 */
public class StewardDataAccessException extends DataAccessException{
    
    public StewardDataAccessException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
