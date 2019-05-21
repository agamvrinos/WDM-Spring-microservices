package wdm.project.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<ExceptionResponse> handleControllerError(HttpServletRequest req, OrderException ex) {
        ExceptionResponse response = new ExceptionResponse(ex.getErrorMessage(), ex.getHttpStatus());
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
