package cz.fi.muni.pa165.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Karel Jiranek
 */
@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason="The requested resource do not exists.")
public class ResourceAlreadyExistingException extends RuntimeException {
}
