package cz.fi.muni.pa165.exceptions;

import org.springframework.dao.DataAccessException;

public class DestinationDataAccessException extends DataAccessException {

    public DestinationDataAccessException(String msg) {
        super(msg);
    }

    public DestinationDataAccessException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
