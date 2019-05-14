package wdm.project.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(UsersException.class)
	public ResponseEntity<ExceptionResponse> handleControllerError(HttpServletRequest req, UsersException ex) {
		ExceptionResponse response = new ExceptionResponse(ex.getErrorMessage(), ex.getHttpStatus());
		return ResponseEntity.status(ex.getHttpStatus()).body(response);
	}
}
