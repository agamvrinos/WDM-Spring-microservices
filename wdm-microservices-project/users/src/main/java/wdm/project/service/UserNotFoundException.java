package wdm.project.service;

public class UserNotFoundException extends RuntimeException {
    private long uid;

    public UserNotFoundException(long id) {
        super("could not find user " + id);
        uid = id;
    }

    public long getUid() {
        return uid;
    }
}
