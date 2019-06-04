package wdm.project.service.clients;

import org.springframework.cloud.openfeign.FeignClient;
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
	Item getItem(@PathVariable("id") Long itemId);

	@GetMapping("/stock/availability/{id}")
	Integer getItemAvailability(@PathVariable("id") Long itemId);

	@PostMapping("/stock/item/create/")
	Long createItem(@RequestBody Item requestItem);

	@PostMapping("/stock/add/{item_id}/{number}")
	void addItem(@PathVariable("item_id") Long itemId, @PathVariable("number") Integer itemNumber);

	@PostMapping("/stock/subtract")
	Integer subtractItems(@RequestBody List<ItemInfo> itemInfos);

    @PostMapping("/add")
    public void addItems(@RequestBody List<ItemInfo> items);
}
