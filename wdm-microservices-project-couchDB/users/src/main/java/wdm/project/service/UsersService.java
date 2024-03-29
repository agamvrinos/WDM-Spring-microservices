package wdm.project.service;

import org.ektorp.DocumentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import wdm.project.dto.User;
import wdm.project.exception.UsersException;
import wdm.project.repository.UsersRepository;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    /**
     * Creates a new user.
     *
     * @param requestUser a user object containing the information
     * to be stored
     * @return the created user
     */
    public User createUser(User requestUser) {
        User user = new User();
        user.setName(requestUser.getName());
        user.setCredit(requestUser.getCredit());
        usersRepository.add(user);
        return user;
    }

    /**
     * Removes the user of the provided id.
     *
     * @param id the id of the user to be removed
     * @throws UsersException in case the call to findUser fails
     */
    public void removeUser(String id) throws UsersException {
        User storedUser = findUser(id);
        usersRepository.remove(storedUser);
    }

    /**
     * Updates the {@code credit} of the user with the provided id.
     *
     * @param id the id of the user to be updates
     * @param requestUser a user object that contains the new
     * {@code credit} value
     * @throws UsersException in case the call to findUser fails
     */
    public void updateUser(String id, User requestUser) throws UsersException {
        User storedUser = findUser(id);
        Integer requestCredit = requestUser.getCredit();
        storedUser.setCredit(requestCredit);
        usersRepository.update(storedUser);
    }

    /**
     * Finds the user with the provided id.
     *
     * @param id the id of the user to be fetched
     * @return the user object with the provided i
     * @throws UsersException in case of invalid id
     */
    public User findUser(String id) throws UsersException {
        if (id == null) {
            throw new UsersException("Id was not provided", HttpStatus.BAD_REQUEST);
        }
        try{
            return usersRepository.get(id);
        } catch (DocumentNotFoundException exception){
            throw new UsersException("There is no user with id \"" + id + "\"", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Returns the {@code credit} of the user with the provided id.
     *
     * @param id the id of the user
     * @return the credit of the user with the provided id
     * @throws UsersException in case the call to findUser fails
     */
    public Integer getUserCredit(String id) throws UsersException {
        User storedUser = findUser(id);
        return storedUser.getCredit();
    }

    /**
     * Adds credit to the user of the provided id.
     *
     * @param id the id of the user, the credit of which is
     * going to be updated
     * @param amount the amount to add to the existing credit
     * @throws UsersException in case the call to findUser fails
     */
    public void addCredit(String id, Integer amount) throws UsersException {
        User user = findUser(id);
        Integer credit = user.getCredit();
        user.setCredit(credit + amount);
        usersRepository.update(user);
    }

    /**
     * Removed credit from the user of the provided id.
     *
     * @param id the id of the user, the credit of which is
     * going to be updated
     * @param amount the amount to remove from the existing credit
     * @throws UsersException in case the current credits are insufficient
     */
    public void subtractCredit(String id, Integer amount) throws UsersException {
        User user = findUser(id);
        Integer credit = user.getCredit();
        if (amount > credit) {
            throw new UsersException("Insufficient credit", HttpStatus.BAD_REQUEST);
        } else {
            user.setCredit(credit - amount);
            usersRepository.update(user);
        }
    }
}
