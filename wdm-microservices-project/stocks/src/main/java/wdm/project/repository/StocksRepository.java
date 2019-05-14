package wdm.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wdm.project.dto.Item;

@Repository
public interface StocksRepository extends JpaRepository<Item, Long> {
}
