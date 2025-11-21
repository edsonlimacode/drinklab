package com.drinklab.api.exceptions;


import com.drinklab.api.exceptions.customExceptions.BadRequestException;
import com.drinklab.api.exceptions.customExceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class GlobalHandleExceptions extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {

        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;

        ProblemDetails problemDetails = getProblemDetails( internalServerError, ex.getMessage())
                .build();

        return super.handleExceptionInternal(ex, problemDetails, new HttpHeaders(), internalServerError, request);

    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException ex) {

        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ProblemDetails problemDetails = getProblemDetails( badRequest, ex.getMessage())
                .build();

        return ResponseEntity.badRequest().body(problemDetails);

    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException ex) {

        HttpStatus notFound = HttpStatus.BAD_REQUEST;

        ProblemDetails problemDetails = getProblemDetails(notFound, ex.getMessage())
                .build();

        return ResponseEntity.status(notFound).body(problemDetails);

    }

    public static ProblemDetails.ProblemDetailsBuilder getProblemDetails(HttpStatus status, String detail) {
        return ProblemDetails
                .builder()
                .status(status.value())
                .title(HttpStatus.valueOf(status.value()).getReasonPhrase())
                .detail(detail)
                .date(LocalDateTime.now().toString());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        List<ProblemDetails.Field> fieldList = ex.getBindingResult().getFieldErrors().stream().map(fieldError -> {

            String message = this.messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());

            return ProblemDetails.Field.builder()
                    .field(fieldError.getField())
                    .message(message)
                    .build();
        }).toList();

        ProblemDetails problemDetails = getProblemDetails(badRequest, "Um ou mais campos estão inválidos, verique os campos e tente novamente")
                .fields(fieldList)
                .build();

        return ResponseEntity.status(badRequest).body(problemDetails);
    }

}
