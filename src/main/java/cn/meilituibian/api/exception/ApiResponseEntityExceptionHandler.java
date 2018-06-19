package cn.meilituibian.api.exception;

import cn.meilituibian.api.aspect.ResponseEntityAspect;
import org.apache.logging.log4j.LogManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class ApiResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(ApiResponseEntityExceptionHandler.class);
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllException(Exception ex , WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
                request.getDescription(false));
        LOGGER.error(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(ApiException.class)
    public final ResponseEntity<ExceptionResponse> handleApiException(ApiException ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
                request.getDescription(false));
        LOGGER.error(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, ex.getHttpStatus());
    }
}
