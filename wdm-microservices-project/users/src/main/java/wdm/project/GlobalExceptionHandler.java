package wdm.project;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import wdm.project.service.UnsufficientCreditException;
import wdm.project.service.UserNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({UserNotFoundException.class, UnsufficientCreditException.class})
    public final ResponseEntity<Object> handleException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();

        if (ex instanceof UserNotFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            UserNotFoundException unfe = (UserNotFoundException) ex;

            return handleUserNotFoundException(unfe, headers, status, request);
        }
        else if (ex instanceof UnsufficientCreditException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            UnsufficientCreditException cnae = (UnsufficientCreditException) ex;

            return handleUnsufficientCreditException(cnae, headers, status, request);
        }
        else {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleExceptionInternal(ex, null, headers, status, request);
        }
    }

    private ResponseEntity<Object> handleExceptionInternal(Exception ex, Object o, HttpHeaders headers,
                                                           HttpStatus status, WebRequest request) {
        return new ResponseEntity<>("unexpected error", status);
    }

    private ResponseEntity<Object> handleUnsufficientCreditException(UnsufficientCreditException uce, HttpHeaders headers,
                                                                     HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(String.format("User %d has unsufficient credit", uce.getUid()), status);
    }

    private ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException unfe, HttpHeaders headers,
                                                               HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(String.format("User %d not found", unfe.getUid()), status);
    }


}
