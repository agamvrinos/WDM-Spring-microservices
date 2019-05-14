package wdm.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wdm.project.dto.Item;
import wdm.project.repository.StocksRepository;

@Service
public class StocksService {

    @Autowired
    private StocksRepository stocksRepository;

    /**
     * Returns the Item instance with the provided id.
     *
     * @param itemId the id of the instance to be fetched
     * @return the Item with the provided id
     */
    public Item getItem(Long itemId) {
        if (itemId == null) {
            throw new RuntimeException("Id was not provided");
        }
        boolean existsStocks = stocksRepository.existsById(itemId);
        if (!existsStocks) {
            throw new RuntimeException("There was no stock with the provided id");
        }
        return stocksRepository.findById(itemId).orElseThrow(RuntimeException::new);
    }

    /**
     * Returns the availability of the Item with the
     * provided id.
     *
     * @param itemId the id of the item
     * @return the stock of the item
     */
    public Integer getItemAvailability(Long itemId) {
        Item item = getItem(itemId);
        return item.getStock();
    }

    /**
     * Creates a new Item based on the provided
     * {@code requestItem}.
     *
     * @param requestItem the request item
     * @return the id of the stored item
     */
    public Long createItem(Item requestItem) {
        Item item = new Item();
        item.setTitle(requestItem.getTitle());
        item.setStock(requestItem.getStock());
        item.setPrice(requestItem.getPrice());
        Item storedItem = stocksRepository.save(item);
        return storedItem.getId();
    }

    /**
     * Adds stock to the Item instance with the provided id.
     *
     * @param itemId the id of the Item, the stock of which is
     * is goings to be updated
     * @param additionalStock the stock to be added
     */
    public void addItem(Long itemId, Integer additionalStock) {
        Item item = getItem(itemId);
        Integer currentStock = item.getStock();
        item.setStock(currentStock + additionalStock);
        stocksRepository.save(item);
    }

    /**
     * Subtracts stock from the Item instance with the provided id.
     *
     * @param itemId the id of the Item, the stock of which is
     *      * is goings to be updated
     * @param subtractedStock the stock to be subtracted
     */
    public void subtractItem(Long itemId, Integer subtractedStock) {
        Item item = getItem(itemId);
        Integer currentStock = item.getStock();
        if (subtractedStock > currentStock) {
            throw new RuntimeException("Stock cannot be negative");
        }
        item.setStock(currentStock - subtractedStock);
        stocksRepository.save(item);
    }
}
