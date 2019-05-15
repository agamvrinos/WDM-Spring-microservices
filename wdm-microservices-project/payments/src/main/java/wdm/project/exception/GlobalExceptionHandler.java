package wdm.project.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({MethodArgumentTypeMismatchException.class, IllegalArgumentException.class})
    protected ResponseEntity<Object> handleWrongInput(final RuntimeException excep, final WebRequest request) {
        return handleExceptionInternal(excep, "Wrong input is supplied.", new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ExceptionResponse> handleControllerError(HttpServletRequest req, PaymentException ex) {
        ExceptionResponse response = new ExceptionResponse(ex.getErrorMessage(), ex.getHttpStatus());
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
