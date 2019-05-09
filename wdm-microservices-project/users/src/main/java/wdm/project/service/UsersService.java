package wdm.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wdm.project.dto.User;
import wdm.project.exception.UnsufficientCreditException;
import wdm.project.exception.UserNotFoundException;
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
        return usersRepository.save(user);
    }

    /**
     * Removes the user of the provided id.
     *
     * @param id the id of the user to be removed
     */
    public void removeUser(Long id) {
        User storedUser = findUser(id);
        usersRepository.delete(storedUser);
    }

    /**
     * Updates the {@code credit} of the user with the provided id.
     *
     * @param id the id of the user to be updates
     * @param requestUser a user object that contains the new
     * {@code credit} value
     */
    public void updateUser(Long id, User requestUser) {
        User storedUser = findUser(id);
        Integer requestCredit = requestUser.getCredit();
        storedUser.setCredit(requestCredit);
        usersRepository.save(storedUser);
    }

    /**
     * Finds the user with the provided id.
     *
     * @param id the id of the user to be fetched
     * @return the user object with the provided i
     * @throws RuntimeException in case of invalid id
     */
    public User findUser(Long id) throws RuntimeException {
        if (id == null) {
            throw new RuntimeException("Id was not provided");
        }
        boolean existsUser = usersRepository.existsById(id);
        if (!existsUser) {
            throw new UserNotFoundException(id);
        }
        return usersRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    /**
     * Returns the {@code credit} of the user with the provided id.
     *
     * @param id the id of the user
     * @return the credit of the user with the provided id
     */
    public Integer getUserCredit(Long id) {
        User storedUser = findUser(id);
        return storedUser.getCredit();
    }

    /**
     * Adds credit to the user of the provided id.
     *
     * @param id the id of the user, the credit of which is
     * going to be updated
     * @param amount the amount to add to the existing credit
     */
    public void addCredit(Long id, Integer amount) {
        User user = findUser(id);
        Integer credit = user.getCredit();
        user.setCredit(credit + amount);
        usersRepository.save(user);
    }

    /**
     * Removed credit from the user of the provided id.
     *
     * @param id the id of the user, the credit of which is
     * going to be updated
     * @param amount the amount to remove from the existing credit
     */
    public void subtractCredit(Long id, Integer amount) {
        User user = findUser(id);
        Integer credit = user.getCredit();
        if (amount > credit) {
            throw new UnsufficientCreditException(id);
        } else {
            user.setCredit(credit - amount);
            usersRepository.save(user);
        }
    }
}
