package wdm.project.exception;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class ExceptionResponse {

    private Date timestamp;
    private String message;
    private HttpStatus status;
    private int errorCode;

    public ExceptionResponse(String message, HttpStatus status) {
        timestamp = new Date();
        this.message = message;
        this.status = status;
        this.errorCode = this.status.value();
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "ExceptionResponse{" +
               "timestamp=" + timestamp +
               ", message='" + message + '\'' +
               ", status=" + status +
               ", errorCode=" + errorCode +
               '}';
    }
}
