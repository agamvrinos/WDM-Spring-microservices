package wdm.project.endpoint;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Integer id) {
        super("could not find user " + id);
    }
}
