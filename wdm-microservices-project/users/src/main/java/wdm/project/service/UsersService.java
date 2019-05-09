package wdm.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wdm.project.dto.User;
import wdm.project.endpoint.UserNotFoundException;
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
        User inserted = usersRepository.save(user);
        return inserted;
    }

    public void removeUser(Integer id) {
        usersRepository.deleteById(id);
    }

    public void updateUser(Integer id, User user) {
        User update = new User();
        user.setId(id);
        user.setCredit(user.getCredit());
        user.setName(user.getName());
        usersRepository.save(update);
    }

    public Optional<User> findUser(Integer id) {
        return usersRepository.findById(id);
    }

    public Double getUserCredit(Integer id) {
        return usersRepository.getOne(id).getCredit();
    }

    public void addCredit(Integer id, Double amount) {
        //TODO: implement me
    }

    public void subtractCredit(Integer id, Double amount) {
        User user = usersRepository.getOne(id);
    }
}
