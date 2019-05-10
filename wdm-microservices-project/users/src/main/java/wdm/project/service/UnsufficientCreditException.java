package wdm.project.service;

public class UnsufficientCreditException extends RuntimeException {

    private int uid;

    public UnsufficientCreditException(int userId) {
        super(String.format("user %d has unsufficient credit", userId));
        uid = userId;
    }

    public int getUid() {
        return uid;
    }
}
