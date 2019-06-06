package wdm.project.service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import wdm.project.dto.Item;
import wdm.project.dto.JournalEntry;
import wdm.project.dto.JournalEntryId;
import wdm.project.dto.remote.ItemInfo;
import wdm.project.enums.Event;
import wdm.project.enums.Status;
import wdm.project.exception.StockException;
import wdm.project.repository.JournalRepository;
import wdm.project.repository.StocksRepository;

@Service
@Transactional
public class StocksService {

    @Autowired
    private StocksRepository stocksRepository;
    @Autowired
    private JournalRepository journalRepository;

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
    public void addItem(Long itemId, Integer additionalStock) throws StockException {
        Item item = getItem(itemId);
        Integer currentStock = item.getStock();
        item.setStock(currentStock + additionalStock);
        stocksRepository.save(item);
    }

    /**
     * Subtracts stock from the Item instance with the provided id.
     *
     * @param transactionId the ID of the transaction to remove the stock for
     * @param itemInfos item informations for all items which have to be subtracted.
     * @return the total price of all subtracted items
     * @throws StockException when the item with the provided ID is not
     * found or the stock is insufficient.
     */
    public Integer subtractItems(Long transactionId, List<ItemInfo> itemInfos) throws StockException {
        JournalEntry subtractEntry = getJournalEntry(new JournalEntryId(transactionId, Event.SUBTRACT_STOCK));
        if (subtractEntry.getStatus().equals(Status.FAILURE.getValue())) {
            throw new StockException("The stock could not be reduced for transaction with ID " + transactionId + ".", HttpStatus.BAD_REQUEST);
        } else if (subtractEntry.getStatus().equals(Status.SUCCESS.getValue())) {
            return subtractEntry.getPrice();
        } else {
            List<Item> items = new ArrayList<>();
            int totalPrice = 0;
            for (ItemInfo itemInfo : itemInfos) {
                Item item = getItem(itemInfo.getId());
                Integer currentStock = item.getStock();
                if (itemInfo.getAmount() > currentStock) {
                    subtractEntry.setStatus(Status.FAILURE);
                    journalRepository.save(subtractEntry);
                    throw new StockException("The stock of item ID " + itemInfo.getId() + " is " + currentStock +
                                             " and can therefore not be reduced by " + itemInfo.getAmount() + ".", HttpStatus.BAD_REQUEST);
                }
                item.setStock(currentStock - itemInfo.getAmount());
                items.add(item);
                totalPrice += itemInfo.getAmount() * item.getPrice();
            }
            stocksRepository.saveAll(items);
            subtractEntry.setStatus(Status.SUCCESS);
            subtractEntry.setPrice(totalPrice);
            journalRepository.save(subtractEntry);
            return totalPrice;
        }
    }

    /**
     * Adds stock from the item with the provided IDs.
     *
     * @param transactionId the ID of the transaction to add the stock for
     * @param itemInfos the item IDs with the amount of stock to add
     * @throws StockException when an item ID is not found
     */
    public void addItems(Long transactionId, List<ItemInfo> itemInfos) throws StockException {
        JournalEntry addEntry = getJournalEntry(new JournalEntryId(transactionId, Event.ADD_STOCK));
        if (addEntry.getStatus().equals(Status.PENDING)) {
            List<Item> items = new ArrayList<>();
            for (ItemInfo itemInfo : itemInfos) {
                Item item = getItem(itemInfo.getId());
                item.setStock(item.getStock() + itemInfo.getAmount());
                items.add(item);
            }
            stocksRepository.saveAll(items);
            addEntry.setStatus(Status.SUCCESS);
            journalRepository.save(addEntry);
        }
    }

    private JournalEntry getJournalEntry(JournalEntryId id) {
        if (journalRepository.existsById(id)) {
            return journalRepository.getOne(id);
        } else {
            return new JournalEntry(id, Status.PENDING, -1);
        }
    }
}
