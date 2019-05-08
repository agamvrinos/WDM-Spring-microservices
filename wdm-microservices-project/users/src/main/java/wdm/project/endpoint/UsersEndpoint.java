package wdm.project.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wdm.project.dto.User;
import wdm.project.service.UsersService;

@RestController
@RequestMapping("/users")
public class UsersEndpoint {

	@Autowired
	private UsersService usersService;

	@PostMapping("/create")
	public void createUser(@RequestBody User requestUser) {
		usersService.createUser(requestUser);
	}

	@DeleteMapping("/remove/{id}")
	public void removeUser(@PathVariable("id") Integer id) {
		usersService.removeUser(id);
	}

	@PutMapping("/update/{id}")
	public void updateUser(@PathVariable("id") Integer id) {
		usersService.updateUser(id);
	}

	@GetMapping("/find/{id}")
	public void findUser(@PathVariable("id") Integer id) {
		usersService.findUser(id);
	}

	@GetMapping("/credit/{id}")
	public void getUserCredit(@PathVariable("id") Integer id) {
		usersService.getUserCredit(id);
	}

	@PostMapping("/credit/subtract/{id}/{amount}")
	public void subtractCredit(
			@PathVariable("id") Integer id,
			@PathVariable("amount") String amount
	) {
		usersService.subtractCredit(id, amount);
	}

	@PostMapping("/credit/add/{id}/{amount}")
	public void addCredit(
			@PathVariable("id") Integer id,
			@PathVariable("amount") String amount
	) {
		usersService.addCredit(id, amount);
	}
}
