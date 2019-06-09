package wdm.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wdm.project.dto.Item;
import wdm.project.dto.remote.ItemInfo;
import wdm.project.exception.StockException;
import wdm.project.repository.StocksRepository;

@Service
@CacheConfig(cacheNames={"items"})
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
    public Item getItem(Long itemId) throws StockException {
        if (itemId == null) {
            throw new StockException("Provided item ID was null. Please provide a valid ID.", HttpStatus.BAD_REQUEST);
        }
        boolean existsStocks = stocksRepository.existsById(itemId);
        if (!existsStocks) {
            throw new StockException("The item with ID " + itemId + " was not found.", HttpStatus.NOT_FOUND);
        }
        return stocksRepository.findById(itemId).orElseThrow(RuntimeException::new);
    }

    /**
     * Returns the availability of the Item with the
     * provided id.
     *
     * @param itemId the id of the item
     * @return the stock of the item
     * @throws StockException when the item with the provided ID is not found
     */
    public Integer getItemAvailability(Long itemId) throws StockException {
        Item item = getItem(itemId);
        return item.getStock();
    }

    @Cacheable
    public Boolean checkItem(Long itemId){ return  stocksRepository.existsById(itemId); }

    /**
     * Creates a new Item based on the provided
     * {@code requestItem}.
     *
     * @param requestItem the request item
     * @return the created item
     */
    public Item createItem(Item requestItem) {
        Item item = new Item();
        item.setTitle(requestItem.getTitle());
        item.setStock(requestItem.getStock());
        item.setPrice(requestItem.getPrice());
        return stocksRepository.save(item);
    }

    /**
     * Adds stock to the Item instance with the provided id.
     *
     * @param itemId the id of the Item, the stock of which is
     * is goings to be updated
     * @param additionalStock the stock to be added
     * @throws StockException when the item with the provided ID is not found.
     */
    @Transactional(rollbackFor = StockException.class)
    public void addItem(Long itemId, Integer additionalStock) throws StockException {
        Item item = getItem(itemId);
        Integer currentStock = item.getStock();
        item.setStock(currentStock + additionalStock);
        stocksRepository.save(item);
    }

    /**
     * Subtracts stock from the Item instance with the provided id.
     *
     * @param itemInfos item informations for all items which have to be subtracted.
     * @return the total price of all subtracted items
     * @throws StockException when the item with the provided ID is not
     * found or the stock is insufficient.
     */
    @Transactional(rollbackFor = StockException.class)
    public Integer subtractItems(List<ItemInfo> itemInfos) throws StockException {
        List<Item> items = new ArrayList<>();
        int totalPrice = 0;
        for(ItemInfo itemInfo: itemInfos) {
            Item item  = getItem(itemInfo.getId());
            Integer currentStock = item.getStock();
            if (itemInfo.getAmount() > currentStock) {
                throw new StockException("The stock of item ID " + itemInfo.getId() + " is " + currentStock +
                                         " and can therefore not be reduced by " + itemInfo.getAmount() + ".", HttpStatus.BAD_REQUEST);
            }
            item.setStock(currentStock - itemInfo.getAmount());
            items.add(item);
            totalPrice += itemInfo.getAmount() * item.getPrice();
        }
        stocksRepository.saveAll(items);
        return totalPrice;
    }

    /**
     * Adds stock from the item with the provided IDs.
     *
     * @param itemInfos the item IDs with the amount of stock to add
     * @throws StockException when an item ID is not found
     */
    @Transactional(rollbackFor = StockException.class)
    public void addItems(List<ItemInfo> itemInfos) throws StockException {
        List<Item> items = new ArrayList<>();
        for (ItemInfo itemInfo: itemInfos) {
            Item item = getItem(itemInfo.getId());
            item.setStock(item.getStock() + itemInfo.getAmount());
            items.add(item);
        }
        stocksRepository.saveAll(items);
    }
}
