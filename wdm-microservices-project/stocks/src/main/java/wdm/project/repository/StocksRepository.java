package wdm.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import wdm.project.dto.Item;

import java.util.List;

@Repository
public interface StocksRepository extends JpaRepository<Item, Long> {

    @Query(value = "SELECT id FROM item i")
    List<Long> getAllItemIds();

}
