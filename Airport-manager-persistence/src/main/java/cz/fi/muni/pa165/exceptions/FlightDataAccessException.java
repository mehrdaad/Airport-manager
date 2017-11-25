package cz.fi.muni.pa165.exceptions;

import org.springframework.dao.DataAccessException;

public class FlightDataAccessException extends DataAccessException {

    public FlightDataAccessException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
