package wdm.project.exception;

import org.springframework.http.HttpStatus;

public class OrderException extends Exception {

    private String errorMessage;
    private HttpStatus httpStatus;

    public String getErrorMessage() {
        return errorMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public OrderException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public OrderException(String errorMessage, HttpStatus httpStatus) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    public OrderException() {
        super();
    }
}
