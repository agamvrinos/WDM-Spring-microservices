package wdm.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import wdm.project.dto.Item;
import wdm.project.dto.JournalEntry;
import wdm.project.dto.remote.ItemInfo;
import wdm.project.enums.Event;
import wdm.project.enums.Status;
import wdm.project.exception.StockException;
import wdm.project.repository.JournalRepository;
import wdm.project.repository.StocksRepository;

import java.util.List;

@Service
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
     * @param transactionId the ID of the transaction to remove the stock for
     * @param itemInfos item informations for all items which have to be subtracted.
     * @return the total price of all subtracted items
     * @throws StockException when the item with the provided ID is not
     * found or the stock is insufficient.
     */
    public Integer subtractItems(String transactionId, List<ItemInfo> itemInfos) throws StockException {

            int totalPrice = 0;
            int idxOfFailure = 0;

            for (ItemInfo itemInfo : itemInfos) {

                Item item = getItem(itemInfo.getId());
                Integer currentStock = item.getStock();
                JournalEntry subtractEntry = getJournalEntry(transactionId + "-"+ item.getId() +"-" + Event.SUBTRACT_STOCK);

                if (journalRepository.contains(subtractEntry.getId())) {
                // If entry exists cases
                    String status = journalRepository.get(subtractEntry.getId()).getStatus();

                    if (status.equals(Status.PENDING.getValue())) {
                        // If entry exists and is pending
                        if (itemInfo.getAmount() > currentStock) {
                            subtractEntry.setStatus(Status.FAILURE);
                            journalRepository.update(subtractEntry);
                            rollbackItemSubtraction(transactionId, itemInfos, idxOfFailure);
                            throw new StockException("The stock of item ID " + itemInfo.getId() + " is " + currentStock +
                                    " and can therefore not be reduced by " + itemInfo.getAmount() + ".", HttpStatus.BAD_REQUEST);
                        }
                        item.setStock(currentStock - itemInfo.getAmount());
                        totalPrice += itemInfo.getAmount() * item.getPrice();
                        subtractEntry.setStatus(Status.SUCCESS);
                        journalRepository.update(subtractEntry);
                        stocksRepository.update(item);
                    }
                    else if (status.equals(Status.FAILURE.getValue())){
                        //If it has failed, rollback from the previous index
                        rollbackItemSubtraction(transactionId, itemInfos, idxOfFailure-1);
                    }
                }else{
                    if (itemInfo.getAmount() > currentStock) {
                        subtractEntry.setStatus(Status.FAILURE);
                        journalRepository.add(subtractEntry);
                        rollbackItemSubtraction(transactionId, itemInfos, idxOfFailure);
                        throw new StockException("The stock of item ID " + itemInfo.getId() + " is " + currentStock +
                                " and can therefore not be reduced by " + itemInfo.getAmount() + ".", HttpStatus.BAD_REQUEST);
                    }
                    item.setStock(currentStock - itemInfo.getAmount());
                    totalPrice += itemInfo.getAmount() * item.getPrice();
                    subtractEntry.setStatus(Status.SUCCESS);
                    journalRepository.add(subtractEntry);
                    stocksRepository.update(item);

                }
                idxOfFailure++;
            }
            return totalPrice;
    }

    /**
     *
     * Adds stock from the item with the provided IDs.
     *
     * @param transactionId the ID of the transaction to add the stock for
     * @param itemInfos the item IDs with the amount of stock to add
     * @throws StockException when an item ID is not found
     */
    public void addItems(String transactionId, List<ItemInfo> itemInfos) throws StockException {

            for (ItemInfo itemInfo : itemInfos) {

                Item item = getItem(itemInfo.getId());
                Integer currentStock = item.getStock();
                item.setStock(currentStock + itemInfo.getAmount());

                JournalEntry addEntry = getJournalEntry(transactionId + "-" + item.getId() +"-" + Event.ADD_STOCK);
                addEntry.setStatus(Status.SUCCESS);

                if(journalRepository.contains(addEntry.getId())){
                    if (journalRepository.get(addEntry.getId()).getStatus().equals(Status.PENDING.getValue())) {
                        // If it exists and is PENDING
                        stocksRepository.update(item);
                        journalRepository.update(addEntry);
                    }
                }
                else{
                    stocksRepository.update(item);
                    journalRepository.add(addEntry);
                }
            }

    }

    private void rollbackItemSubtraction(String transactionId, List<ItemInfo> itemInfos, int idxOfFailure) throws StockException {

        for(int i=0;i<idxOfFailure;i++) {

            ItemInfo itemInfo = itemInfos.get(i);
            Item item = getItem(itemInfo.getId());
            JournalEntry rollbackEntry = getJournalEntry(transactionId + "-"+ item.getId() +"-" + Event.SUBTRACT_STOCK);
            rollbackEntry.setStatus(Status.FAILURE);
            Integer currentStock = item.getStock();
            item.setStock(currentStock + itemInfo.getAmount());
            journalRepository.update(rollbackEntry);
            stocksRepository.update(item);

        }
    }

    private JournalEntry getJournalEntry(String id) {
        if (journalRepository.contains(id)) {
            JournalEntry journalEntry = journalRepository.get(id);
            journalEntry.setId(id);
            return journalEntry;
        } else {
            return new JournalEntry(id, Status.PENDING);
        }
    }
}
