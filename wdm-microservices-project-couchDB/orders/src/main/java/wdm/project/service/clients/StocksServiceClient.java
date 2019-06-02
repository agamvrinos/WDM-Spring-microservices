package wdm.project.service.clients;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import wdm.project.dto.ItemInfo;
import wdm.project.dto.remote.Item;

import java.util.List;

@FeignClient(name = "stocks-service")
public interface StocksServiceClient {

	@GetMapping("/stock/item/{id}")
	Item getItem(@PathVariable("id") String itemId);

	@GetMapping("/stock/availability/{id}")
	Integer getItemAvailability(@PathVariable("id") String itemId);

	@PostMapping("/stock/item/create/")
	String createItem(@RequestBody Item requestItem);

	@PostMapping("/stock/add/{item_id}/{number}")
	void addItem(@PathVariable("item_id") String itemId,@PathVariable("number") Integer itemNumber);

	@PostMapping("/stock/subtract/{item_id}/{number}")
	void subtractItem(@PathVariable("item_id") String itemId, @PathVariable("number") Integer itemNumber);

	@PostMapping("/stock/subtract/{transaction_id}")
	Integer subtractItems(@PathVariable("transaction_id") String transactionId, @RequestBody List<ItemInfo> itemInfos);

	@PostMapping("/stock/add/{transaction_id}")
	void addItems(@PathVariable("transaction_id") String transactionId, @RequestBody List<ItemInfo> items);
}
