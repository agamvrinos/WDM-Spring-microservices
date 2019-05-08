package wdm.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wdm.project.dto.User;
import wdm.project.repository.UsersRepository;

@Service
public class UsersService {

	@Autowired
	private UsersRepository usersRepository;

	public void createUser(User requestUser) {
		User user = new User();
		user.setName(requestUser.getName());
		user.setCredit(requestUser.getCredit());
		usersRepository.save(user);
	}

	public void removeUser(Integer id) {
		//TODO: implement me
	}

	public void updateUser(Integer id) {
		//TODO: implement me
	}

	public void findUser(Integer id) {
		//TODO: implement me
	}

	public void getUserCredit(Integer id) {
		//TODO: implement me
	}

	public void addCredit(Integer id, String amount) {
		//TODO: implement me
	}

	public void subtractCredit(Integer id, String amount) {
		//TODO: implement me
	}
}
