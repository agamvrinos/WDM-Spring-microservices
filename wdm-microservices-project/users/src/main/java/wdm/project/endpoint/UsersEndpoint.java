package wdm.project.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wdm.project.service.UsersService;

@RestController
@RequestMapping("/users")
public class UsersEndpoint {

	@Autowired
	private UsersService usersService;

	@PostMapping("")
	public void createUser() {
		usersService.createUser();
	}

	@DeleteMapping("")
	public void removeUser() {
		usersService.removeUser();
	}

	@PutMapping("")
	public void updateUser() {
		usersService.updateUser();
	}

	@GetMapping("")
	public void findUser() {
		usersService.findUser();
	}

	@GetMapping("")
	public void getUserCredit() {
		usersService.getUserCredit();
	}

	@PostMapping("")
	public void addCredit() {
		usersService.addCredit();
	}

	@PostMapping("")
	public void subtractCredit() {
		usersService.subtractCredit();
	}
}
