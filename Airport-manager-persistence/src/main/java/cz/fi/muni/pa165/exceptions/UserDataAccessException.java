package cz.fi.muni.pa165.exceptions;

import org.springframework.dao.DataAccessException;

/**
 * @author Robert Duriancik
 */
public class UserDataAccessException extends DataAccessException {
    public UserDataAccessException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
