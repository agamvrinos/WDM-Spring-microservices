package wdm.project.service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import wdm.project.dto.remote.User;

@FeignClient(name = "users-service")
public interface UsersServiceClient {

	@PostMapping("/users/create")
	Long createUser(@RequestBody User requestUser);

	@DeleteMapping("/users/remove/{id}")
	void removeUser(@PathVariable("id") Long id);

	@PutMapping("/users/update/{id}")
	void updateUser(@PathVariable("id") Long id, @RequestBody User user);

	@GetMapping("/users/find/{id}")
	User findUser(@PathVariable("id") Long id);

	@GetMapping("/users/credit/{id}")
	Integer getUserCredit(@PathVariable("id") Long id);

	@PostMapping("/users/credit/subtract/{id}/{transaction_id}/{amount}")
	void subtractCredit(@PathVariable("id") Long id,
                        @PathVariable("transaction_id") Long transactionId,
                        @PathVariable("amount") Integer amount);

	@PostMapping("/users/credit/add/{id}/{amount}")
    void addCredit(@PathVariable("id") Long id, @PathVariable("amount") Integer amount);
}
