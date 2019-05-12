package wdm.project.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wdm.project.dto.Item;
import wdm.project.service.StocksService;

@RestController
@RequestMapping("/stock")
public class StocksEndpoint {

    @Autowired
    private StocksService stocksService;

	@GetMapping("/item/{id}")
    public Item getItem(@PathVariable("id") Long itemId) {
        return stocksService.getItemAvailability(itemId).orElseThrow(
                () -> new ItemNotFoundException(itemId));
    }

    @GetMapping("/availability/{id}")
    public Item getItemAvailability(@PathVariable("id") Long itemId) {
        return stocksService.getItemAvailability(itemId).orElseThrow(
                () -> new ItemNotFoundException(itemId));
    }

    public class ItemNotFoundException extends RuntimeException {
        ItemNotFoundException(Long id) {super("Item with id: " + id+ " does not exist");}
    }
}
