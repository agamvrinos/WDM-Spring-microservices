package wdm.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wdm.project.dto.Item;
import wdm.project.repository.StocksRepository;

@Service
public class StocksService {

    @Autowired
    private StocksRepository stocksRepository;

    public Item getItem(Long itemId) {
        Boolean exists = stocksRepository.existsById(itemId);
        if (!exists) {
            throw new RuntimeException();
        }
        return stocksRepository.findById(itemId).orElseThrow(RuntimeException::new);
    }

    public Integer getItemAvailability(Long itemId) {
        Boolean exists = stocksRepository.existsById(itemId);
        if (!exists) {
            throw new RuntimeException();
        }
        Item item = stocksRepository.findById(itemId).orElseThrow(RuntimeException::new);
        return item.getStock();
    }

    public Long createItem(Item requestItem) {
        Item item = new Item();
        item.setTitle(requestItem.getTitle());
        item.setStock(requestItem.getStock());
        item.setPrice(requestItem.getPrice());
        stocksRepository.save(item);
        return item.getId();
    }

    public void addItem(Long itemId, Integer itemNumber) {
        Item item = stocksRepository.findById(itemId).orElseThrow(RuntimeException::new);
        item.setStock(item.getStock() + itemNumber);
        stocksRepository.save(item);
    }

    public void subtractItem(Long itemId, Integer itemNumber) {
        Item item = stocksRepository.findById(itemId).orElseThrow(RuntimeException::new);
        Integer updatedStock = item.getStock() - itemNumber;
        if (updatedStock < 0) {
            throw new RuntimeException();
        }
        item.setStock(item.getStock() - itemNumber);
        stocksRepository.save(item);
    }
}
