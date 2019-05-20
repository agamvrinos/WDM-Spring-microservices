package wdm.project.endpoint;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wdm.project.dto.Item;
import wdm.project.exception.StockException;
import wdm.project.service.StocksService;

@RestController
@RequestMapping("/stock")
public class StocksEndpoint {

    @Autowired
    private StocksService stocksService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/item/{id}")
    public Item getItem(@PathVariable("id") String itemId) throws StockException {
        return stocksService.getItem(itemId);
    }

    @GetMapping("/availability/{id}")
    public JsonNode getItemAvailability(@PathVariable("id") String itemId) throws StockException{
        Integer availability = stocksService.getItemAvailability(itemId);
        return objectMapper.createObjectNode().put("stock", availability);
    }

    @PostMapping("/item/create/")
    public JsonNode createItem(@RequestBody Item requestItem) {
        String itemId = stocksService.createItem(requestItem);
        return objectMapper.createObjectNode().put("id", itemId);
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
