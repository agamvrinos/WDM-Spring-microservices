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
import wdm.project.exception.UsersException;
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
        String userId = usersService.createUser(requestUser);
        return objectMapper.createObjectNode().put("id", userId);
    }

    @DeleteMapping("/remove/{id}")
    public void removeUser(@PathVariable("id") String id) throws UsersException {
        usersService.removeUser(id);
    }

    @PutMapping("/update/{id}")
    public void updateUser(@PathVariable("id") String id, @RequestBody User user) throws UsersException {
        usersService.updateUser(id, user);
    }

    // TODO RETURN NAME AND AMOUNT
    @GetMapping("/find/{id}")
    public User findUser(@PathVariable("id") String id) throws UsersException {
        return usersService.findUser(id);
    }

    @GetMapping("/credit/{id}")
    public JsonNode getUserCredit(@PathVariable("id") String id) throws UsersException {
        Integer credit = usersService.getUserCredit(id);
        return objectMapper.createObjectNode().put("credit", credit);
    }

    @PostMapping("/credit/subtract/{id}/{amount}")
    public void subtractCredit(
            @PathVariable("id") String id,
            @PathVariable("amount") String amount
    ) throws UsersException {
        // TODO RETURN SUCCESS OR FAILURE
        usersService.subtractCredit(id, Integer.parseInt(amount));
    }

    @PostMapping("/credit/add/{id}/{amount}")
    public void addCredit(
            @PathVariable("id") String id,
            @PathVariable("amount") String amount
    ) throws UsersException {
        usersService.addCredit(id, Integer.parseInt(amount));
    }
}
