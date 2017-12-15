package cz.fi.muni.pa165.exceptions;

/**
 * Exception converted by ExceptionHandler to UNPROCESSABLE_ENTITY HTTP status.
 */
public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
