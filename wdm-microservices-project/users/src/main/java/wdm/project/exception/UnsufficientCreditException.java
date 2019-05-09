package wdm.project.exception;

public class UnsufficientCreditException extends RuntimeException {

    private long uid;

    public UnsufficientCreditException(long userId) {
        super(String.format("user %d has unsufficient credit", userId));
        uid = userId;
    }

    public long getUid() {
        return uid;
    }
}
