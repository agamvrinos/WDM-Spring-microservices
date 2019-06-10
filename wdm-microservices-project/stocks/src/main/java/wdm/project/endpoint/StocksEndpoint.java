package wdm.project.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wdm.project.dto.Item;
import wdm.project.dto.remote.ItemInfo;
import wdm.project.exception.StockException;
import wdm.project.service.StocksService;

@RestController
@RequestMapping("/stock")
public class StocksEndpoint {

    @Autowired
    private StocksService stocksService;

	@GetMapping("/item/{id}")
    public Item getItem(@PathVariable("id") Long itemId) throws StockException {
        return stocksService.getItem(itemId);
    }

    @GetMapping("/checkItem/{id}")
    public Boolean checkItem(@PathVariable("id") Long itemId){
        return stocksService.checkItem(itemId);
    }

    @GetMapping("/availability/{id}")
    public Integer getItemAvailability(@PathVariable("id") Long itemId) throws StockException{
        return stocksService.getItemAvailability(itemId);
    }

    @PostMapping("/item/create")
    public Long createItem(@RequestBody Item requestItem) {
        Item item = stocksService.createItem(requestItem);
        return item.getId();
    }

    @PostMapping("/add/{item_id}/{number}")
    public void addItem(
            @PathVariable("item_id") Long itemId,
            @PathVariable("number") Integer itemNumber
    ) throws StockException {
        stocksService.addItem(itemId, itemNumber);
    }

    @PostMapping("/subtract/{transaction_id}")
    public Integer subtractItems(
            @PathVariable("transaction_id") Long transactionId,
            @RequestBody List<ItemInfo> items
    ) throws StockException {
	    return stocksService.subtractItems(transactionId, items);
    }

    @PostMapping("/add/{transaction_id}")
    public void addItems(
            @PathVariable("transaction_id") Long transactionId,
            @RequestBody List<ItemInfo> items
    ) throws StockException {
	    stocksService.addItems(transactionId, items);
    }
}
