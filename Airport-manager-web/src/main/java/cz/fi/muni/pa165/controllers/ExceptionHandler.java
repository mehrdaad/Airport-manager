package cz.fi.muni.pa165.controllers;

import cz.fi.muni.pa165.exceptions.ErrorResource;
import cz.fi.muni.pa165.exceptions.ResourceAlreadyExistsException;
import cz.fi.muni.pa165.exceptions.ResourceNotFoundException;
import cz.fi.muni.pa165.exceptions.ServerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Converts exceptions to REST responses.
 *
 * @author Robert Duriancik
 */
@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler({Exception.class})
    @ResponseBody
    protected ResponseEntity<ErrorResource> handleException(Exception e) {
        ErrorResource errorResource = new ErrorResource(e.getClass().getSimpleName(), e.getMessage());

        HttpStatus httpStatus;
        if (e instanceof ServerException) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        } else if (e instanceof ResourceNotFoundException) {
            httpStatus = HttpStatus.NOT_FOUND;
        } else if (e instanceof ResourceAlreadyExistsException) {
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        } else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(errorResource, httpStatus);
    }
}
