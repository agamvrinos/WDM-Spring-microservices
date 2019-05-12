package wdm.project.endpoint;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import wdm.project.service.UserNotFoundException;
import wdm.project.service.UsersService;

@RestController
@RequestMapping("/users")
public class UsersEndpoint {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UsersService usersService;

    @PostMapping("/create")
    public JsonNode createUser(@RequestBody User requestUser) {
        long userId = usersService.createUser(requestUser).getId();
        return objectMapper.createObjectNode().put("id", userId);
    }

    @DeleteMapping("/remove/{id}")
    public void removeUser(@PathVariable("id") Long id) {
        usersService.removeUser(id);
    }

    @PutMapping("/update/{id}")
    public void updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        usersService.updateUser(id, user);
    }

    @GetMapping("/find/{id}")
    public User findUser(@PathVariable("id") Long id) {
		return usersService.findUser(id).orElseThrow(
                () -> new UserNotFoundException(id));
    }

    @GetMapping("/credit/{id}")
    public JsonNode getUserCredit(@PathVariable("id") Long id) {
        User u = usersService.findUser(id).orElseThrow(
                () -> new UserNotFoundException(id));
        return objectMapper.createObjectNode().put("credit", u.getCredit());
    }

    @PostMapping("/credit/subtract/{id}/{amount}")
    public void subtractCredit(
            @PathVariable("id") Long id,
            @PathVariable("amount") String amount
    ) {
        usersService.subtractCredit(id, Long.parseLong(amount));
    }

    @PostMapping("/credit/add/{id}/{amount}")
    public void addCredit(
            @PathVariable("id") Long id,
            @PathVariable("amount") String amount
    ) {
        usersService.addCredit(id, Long.parseLong(amount));
    }
}
