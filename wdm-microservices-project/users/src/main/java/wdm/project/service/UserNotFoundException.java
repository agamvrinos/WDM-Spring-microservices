package wdm.project.service;

public class UserNotFoundException extends RuntimeException {
    private int uid;

    public UserNotFoundException(Integer id) {
        super("could not find user " + id);
        uid = id;
    }

    public int getUid() {
        return uid;
    }
}
