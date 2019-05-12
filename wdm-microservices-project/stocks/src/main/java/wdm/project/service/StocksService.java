package wdm.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wdm.project.dto.Item;
import wdm.project.repository.StocksRepository;

import java.util.Optional;

@Service
public class StocksService {

    @Autowired
    private StocksRepository stocksRepository;

    public Optional<Item> getItem(Long itemId) {
        return stocksRepository.findById(itemId);
    }

    public Optional<Item> getItemAvailability(Long itemId) {
        System.out.print(stocksRepository.findById(itemId));
        return stocksRepository.findById(itemId);
    }
}
