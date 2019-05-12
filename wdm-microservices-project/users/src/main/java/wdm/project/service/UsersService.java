package wdm.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wdm.project.dto.User;
import wdm.project.repository.UsersRepository;

import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    public User createUser(User requestUser) {
        User user = new User();
        user.setName(requestUser.getName());
        user.setCredit(requestUser.getCredit());
        return usersRepository.save(user);
    }

    public void removeUser(Long id) {
        usersRepository.deleteById(id);
    }

    public void updateUser(Long id, User user) {
        User update = new User();
        user.setId(id);
        user.setCredit(user.getCredit());
        user.setName(user.getName());
        usersRepository.save(update);
    }

    public Optional<User> findUser(Long id) {
        return usersRepository.findById(id);
    }

//    public Double getUserCredit(Integer id) {
//        return usersRepository.getOne(id).getCredit();
//    }

    public void addCredit(Long id, Long amount) {
        User u = usersRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(id));
        u.setCredit(u.getCredit() + amount);
        usersRepository.save(u);
    }

    public void subtractCredit(Long id, Long amount) {
        User u = usersRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(id));
        if (amount > u.getCredit()) {
            throw new UnsufficientCreditException(id);
        }
        else {
            u.setCredit(u.getCredit() - amount);
            usersRepository.save(u);
        }
    }
}
