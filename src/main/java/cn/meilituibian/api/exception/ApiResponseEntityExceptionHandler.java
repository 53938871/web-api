package cn.meilituibian.api.exception;

import org.apache.logging.log4j.LogManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class ApiResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(ApiResponseEntityExceptionHandler.class);
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponseEntity> handleAllException(Exception ex , WebRequest request) {
        ErrorResponseEntity entity = new ErrorResponseEntity();
        entity.setErrorCode(500);
        entity.setErrorMsg(ex.getMessage());
        entity.setSuccess(false);
        LOGGER.error(ex.getMessage());
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

}
