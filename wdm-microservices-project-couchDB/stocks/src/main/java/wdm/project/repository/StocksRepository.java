package wdm.project.repository;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wdm.project.dto.Item;

@Component
public class StocksRepository extends CouchDbRepositorySupport<Item> {

    @Autowired
    public StocksRepository(CouchDbConnector db) {
        super(Item.class, db);
        initStandardDesignDocument();
    }
}
