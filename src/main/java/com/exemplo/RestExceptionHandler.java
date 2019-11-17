package com.exemplo;

import com.exemplo.api.SpanId;
import com.exemplo.exceptions.ApiExceptions;
import com.exemplo.exceptions.ClienteInvalido;
import com.exemplo.json.ApiError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slog4j.SLogger;
import org.slog4j.SLoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@ControllerAdvice
public class RestExceptionHandler {
    SLogger slog = SLoggerFactory.getLogger(RestExceptionHandler.class);


    private static final HttpHeaders DEFAULT_HTTP_HEADER;

    private final MessageSource messageSource;

    static {
        DEFAULT_HTTP_HEADER = new HttpHeaders();
        DEFAULT_HTTP_HEADER.setContentType(MediaType.APPLICATION_JSON_UTF8);
    }

    @ExceptionHandler({NoHandlerFoundException.class, ClienteInvalido.class})
    @ResponseStatus(value=HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<String> handle404() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        String json = "{ \"data\": {\"error\":\"Resource not found.\"} }";

        return new ResponseEntity<String>(json, headers, HttpStatus.NOT_FOUND);
    }
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @ExceptionHandler({
             BindException.class,
             MethodArgumentNotValidException.class,
             HttpRequestMethodNotSupportedException.class,
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ApiError> processValidationError(Exception ex) {

        String messageError = messageSource.getMessage("erro.requisicao.invalida", null, null);

        log.error(messageError, ex);

        List<FieldError> fieldErrors = null;
        List<ApiError> errors = null;

        if (ex instanceof BindException ) {

            BindException bindException = (BindException) ex;

            BindingResult result = bindException.getBindingResult();
            fieldErrors = result.getFieldErrors();
        }

        if (ex instanceof MethodArgumentNotValidException) {

            MethodArgumentNotValidException bindException = (MethodArgumentNotValidException) ex;

            BindingResult result = bindException.getBindingResult();
            fieldErrors = result.getFieldErrors();
        }

        if(!CollectionUtils.isEmpty(fieldErrors)){
            errors = new ArrayList<>();
            for (FieldError fieldError: fieldErrors) {
                errors.add(
                        ApiError.builder()
                                .fieldName(fieldError.getField())
                                .message(messageSource.getMessage(fieldError.getCode(),
                                        null, messageError, null)).build());
            }
        }

        return ResponseEntity.badRequest().body(ApiError.builder()
                .code(4001)
                .message(messageError)
                .errors(errors).build());
    }

    @ExceptionHandler({ApiExceptions.class})
    public void log(ApiExceptions ex){
        slog.error(SpanId.generateRandomLong(),"EVENT_REQUEST", ex);
    }
}
