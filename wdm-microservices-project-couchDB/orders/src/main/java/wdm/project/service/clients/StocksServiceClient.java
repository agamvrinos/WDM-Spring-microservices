package wdm.project.service.clients;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import wdm.project.dto.remote.Item;

//@FeignClient(name = "stocks-service")
public interface StocksServiceClient {
	//TODO: Dont alter anything here
	@GetMapping("/stock/item/{id}")
	Item getItem(@PathVariable("id") Long itemId);

	@GetMapping("/stock/availability/{id}")
	Integer getItemAvailability(@PathVariable("id") Long itemId);

	@PostMapping("/stock/item/create/")
	Long createItem(@RequestBody Item requestItem);

	@PostMapping("/stock/add/{item_id}/{number}")
	void addItem(@PathVariable("item_id") Long itemId, @PathVariable("number") Integer itemNumber);

	@PostMapping("/stock/subtract/{item_id}/{number}")
	void subtractItem(@PathVariable("item_id") Long itemId, @PathVariable("number") Integer itemNumber);
}
