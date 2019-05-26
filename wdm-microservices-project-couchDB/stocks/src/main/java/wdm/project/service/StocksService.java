package wdm.project.service;

import org.ektorp.DocumentOperationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import wdm.project.dto.Item;
import wdm.project.dto.remote.ItemInfo;
import wdm.project.exception.StockException;
import wdm.project.repository.StocksRepository;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

@Service
public class StocksService {

    @Autowired
    private StocksRepository stocksRepository;

    /**
     * Returns the Item instance with the provided id.
     *
     * @param itemId the id of the instance to be fetched
     * @return the Item with the provided id
     * @throws StockException when the item with the provided ID is not found
     */
    public Item getItem(String itemId) throws StockException {
        if (itemId == null) {
            throw new StockException("Provided item ID was null. Please provide a valid ID.", HttpStatus.BAD_REQUEST);
        }
        boolean existsStocks = stocksRepository.contains(itemId);
        if (!existsStocks) {
            throw new StockException("The item with ID " + itemId + " was not found.", HttpStatus.NOT_FOUND);
        }
        return stocksRepository.get(itemId);
    }

    /**
     * Returns the availability of the Item with the
     * provided id.
     *
     * @param itemId the id of the item
     * @return the stock of the item
     * @throws StockException when the item with the provided ID is not found
     */
    public Integer getItemAvailability(String itemId) throws StockException {
        Item item = getItem(itemId);
        return item.getStock();
    }

    /**
     * Creates a new Item based on the provided
     * {@code requestItem}.
     *
     * @param requestItem the request item
     * @return the stored item
     */
    public Item createItem(Item requestItem) {
        Item item = new Item();
        item.setTitle(requestItem.getTitle());
        item.setStock(requestItem.getStock());
        item.setPrice(requestItem.getPrice());
        stocksRepository.add(item);
        return item;
    }

    /**
     * Adds stock to the Item instance with the provided id.
     *
     * @param itemId the id of the Item, the stock of which is
     * is goings to be updated
     * @param additionalStock the stock to be added
     * @throws StockException when the item with the provided ID is not found.
     */
    public void addItem(String itemId, Integer additionalStock) throws StockException {
        Item item = getItem(itemId);
        Integer currentStock = item.getStock();
        item.setStock(currentStock + additionalStock);
        stocksRepository.update(item);
    }

    /**
     * Subtracts stock from the Item instance with the provided id.
     *
     * @param itemId the id of the Item, the stock of which is
     * is going to be updated
     * @param subtractedStock the stock to be subtracted
     * @throws StockException when the item with the provided ID is not
     * found or the stock is insufficient.
     */
    public void subtractItem(String itemId, Integer subtractedStock) throws StockException {
        Item item = getItem(itemId);
        Integer currentStock = item.getStock();
        if (subtractedStock > currentStock) {
            throw new StockException("The stock of item ID " + itemId + " is " + currentStock +
                    " and can therefore not be reduced by " + subtractedStock + ".", HttpStatus.BAD_REQUEST);
        }
        item.setStock(currentStock - subtractedStock);
        stocksRepository.update(item);
    }

    /**
     * Subtracts stock from the Item instance with the provided id.
     *
     * @param itemInfos item informations for all items which have to be subtracted.
     * @return the total price of all subtracted items
     * @throws StockException when the item with the provided ID is not
     * found or the stock is insufficient.
     */
    public Integer subtractItems(List<ItemInfo> itemInfos) throws StockException {
        int totalPrice = 0;
        for(ItemInfo itemInfo: itemInfos) {
            Item item = getItem(itemInfo.getId());
            Integer currentStock = item.getStock();
            if (itemInfo.getAmount() > currentStock) {
                throw new StockException("The stock of item ID " + itemInfo.getId() + " is " + currentStock +
                        " and can therefore not be reduced by " + itemInfo.getAmount() + ".", HttpStatus.BAD_REQUEST);
            }
            item.setStock(currentStock - itemInfo.getAmount());
            totalPrice += itemInfo.getAmount() * item.getPrice();
            stocksRepository.update(item);
        }
        return totalPrice;
    }
}
