package wdm.project.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wdm.project.dto.Item;
import wdm.project.exception.StockException;
import wdm.project.service.StocksService;

@RestController
@RequestMapping("/stock")
public class StocksEndpoint {

    @Autowired
    private StocksService stocksService;

	@GetMapping("/item/{id}")
    public Item getItem(@PathVariable("id") String itemId) throws StockException {
        return stocksService.getItem(itemId);
    }

    @GetMapping("/availability/{id}")
    public Integer getItemAvailability(@PathVariable("id") String itemId) throws StockException{
        return stocksService.getItemAvailability(itemId);
    }

    @PostMapping("/item/create/")
    public String createItem(@RequestBody Item requestItem) {
        Item item = stocksService.createItem(requestItem);
        return item.getId();
    }

    @PostMapping("/add/{item_id}/{number}")
    public void addItem(@PathVariable("item_id") String itemId,
                        @PathVariable("number") Integer itemNumber
    ) throws StockException {
        stocksService.addItem(itemId, itemNumber);
    }

    @PostMapping("/subtract/{item_id}/{number}")
    public void subtractItem(@PathVariable("item_id") String itemId,
                             @PathVariable("number") Integer itemNumber
    ) throws StockException {
        stocksService.subtractItem(itemId, itemNumber);
    }
}
