package com.tweetapp.exception;

import com.tweetapp.dto.ExceptionDetails;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            final MethodArgumentNotValidException ex) {
        final Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        log.error("{}", errors);
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UsernameAlreadyExists.class)
    public ResponseEntity<Object> handleUserExistsExceptions(final UsernameAlreadyExists ex, final WebRequest request) {
        final ExceptionDetails errorDetails = new ExceptionDetails(
                ex.getLocalizedMessage(), "Username already exists.", request.getDescription(false), new Date());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(TweetDoesNotExistException.class)
    public ResponseEntity<Object> handleUnauthorizedExceptions(final TweetDoesNotExistException ex, final WebRequest request) {
        final ExceptionDetails errorDetails = new ExceptionDetails(
                ex.getLocalizedMessage(), "Could not find Tweet. May be doesn't exist?...", request.getDescription(false), new Date());
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PasswordMisMatchException.class)
    public ResponseEntity<Object> handlePasswordExceptions(final PasswordMisMatchException ex, final WebRequest request) {
        final ExceptionDetails errorDetails = new ExceptionDetails(
                ex.getLocalizedMessage(), "Two Password's didnt match", request.getDescription(false), new Date());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
