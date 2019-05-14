package wdm.project.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wdm.project.dto.Item;
import wdm.project.service.StocksService;

@RestController
@RequestMapping("/stock")
public class StocksEndpoint {

    @Autowired
    private StocksService stocksService;

	@GetMapping("/item/{id}")
    public Item getItem(@PathVariable("id") Long itemId) {
        return stocksService.getItem(itemId);
    }

    @GetMapping("/availability/{id}")
    public Integer getItemAvailability(@PathVariable("id") Long itemId) {
        return stocksService.getItemAvailability(itemId);
    }

    @PostMapping("/item/create/")
    public Long createItem(@RequestBody Item requestItem) {
        return stocksService.createItem(requestItem);
    }

    @PostMapping("/add/{item_id}/{number}")
    public void addItem(@PathVariable("item_id") Long itemId, @PathVariable("number") Integer itemNumber) {
        stocksService.addItem(itemId, itemNumber);
    }

    @PostMapping("/subtract/{item_id}/{number}")
    public void subtractItem(@PathVariable("item_id") Long itemId, @PathVariable("number") Integer itemNumber) {
        stocksService.subtractItem(itemId, itemNumber);
    }
}
