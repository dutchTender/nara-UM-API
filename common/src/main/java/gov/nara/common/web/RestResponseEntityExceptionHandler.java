package gov.nara.common.web;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

import gov.nara.common.web.exception.*;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import gov.nara.common.persistence.exception.MyEntityNotFoundException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    public RestResponseEntityExceptionHandler() {
        super();
    }

    // API

    // 400  duplicate entry will be checked here



    @ExceptionHandler({ ConstraintViolationException.class, MyBadRequestException.class, DataIntegrityViolationException.class })
    public ResponseEntity<Object> handleBadRequest(final RuntimeException ex, final WebRequest request) {
        //final String bodyOfResponse = "This should be application specific";
        return handleExceptionInternal(ex, exceptionMessage(HttpStatus.BAD_REQUEST, ex), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    // bad request name
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        //final String bodyOfResponse = "This should be application specific";
        // ex.getCause() instanceof JsonMappingException, JsonParseException // for additional information later on

        return handleExceptionInternal(ex, exceptionMessage(HttpStatus.BAD_REQUEST, ex), headers, HttpStatus.BAD_REQUEST, request);
    }

    // bad request value
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

        return handleExceptionInternal(ex, exceptionMessage(HttpStatus.BAD_REQUEST, ex), headers, HttpStatus.BAD_REQUEST, request);
    }

    // 403
    // forbidden resource access
    @ExceptionHandler({ MyForbiddenException.class })
    public ResponseEntity<Object> handleForbidden(final MyForbiddenException ex, final WebRequest request) {

        return handleExceptionInternal(ex, exceptionMessage(HttpStatus.FORBIDDEN, ex), new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    // 404
    @ExceptionHandler( value = { gov.nara.common.web.exception.MyResourceNotFoundException.class })
    protected ResponseEntity<Object> handleResourceNotFound(final MyResourceNotFoundException ex, final WebRequest request) {

        return handleExceptionInternal(ex, exceptionMessage(HttpStatus.NOT_FOUND, ex), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }





    @ExceptionHandler({ MyEntityNotFoundException.class })
    protected ResponseEntity<Object> handleNotFound(final MyEntityNotFoundException ex, final WebRequest request) {

        return handleExceptionInternal(ex, exceptionMessage(HttpStatus.NOT_FOUND, ex), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = { EntityNotFoundException.class })
    protected ResponseEntity<Object> handleBadRequest(final EntityNotFoundException ex, final WebRequest request) {

        return handleExceptionInternal(ex, exceptionMessage(HttpStatus.NOT_FOUND, ex), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    // 409

    @ExceptionHandler({ InvalidDataAccessApiUsageException.class, DataAccessException.class, MyConflictException.class })
    protected ResponseEntity<Object> handleConflict(final RuntimeException ex, final WebRequest request) {

        return handleExceptionInternal(ex, exceptionMessage(HttpStatus.CONFLICT, ex), new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    // 4xx

    @ExceptionHandler({ MyPreconditionFailedException.class })
    /*412*/protected ResponseEntity<Object> handlePreconditionFailed(final RuntimeException ex, final WebRequest request) {

        return handleExceptionInternal(ex, exceptionMessage(HttpStatus.PRECONDITION_FAILED, ex), new HttpHeaders(), HttpStatus.PRECONDITION_FAILED, request);
    }

    @ExceptionHandler({ NullPointerException.class, IllegalArgumentException.class, IllegalStateException.class })
    /*500*/public ResponseEntity<Object> handleInternal(final RuntimeException ex, final WebRequest request) {
        logger.error("500 Status Code", ex);

        return handleExceptionInternal(ex, exceptionMessage(HttpStatus.INTERNAL_SERVER_ERROR, ex), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }





    private APIErrorDto exceptionMessage(final  HttpStatus httpStatus, final  Exception ex){
        final  String message = ex.getMessage() == null ? ex.getClass().getSimpleName() : ex.getMessage();
        final  String devMessage = ExceptionUtils.getRootCauseMessage(ex);

        return new APIErrorDto(httpStatus.value(), message, devMessage);
    }


}
