package wdm.project.exception;

import org.springframework.http.HttpStatus;

public class PaymentException extends Exception {

    private String errorMessage;
    private HttpStatus httpStatus;

    public String getErrorMessage() {
        return errorMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public PaymentException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public PaymentException(String errorMessage, HttpStatus httpStatus) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    public PaymentException() {
        super();
    }
}
