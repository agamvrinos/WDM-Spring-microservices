package wdm.project.repository;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wdm.project.dto.Item;

import java.util.List;

@Component
@View(name = "stocks", map = "function(doc) { if (doc.type == 'object' ) emit(doc._id, doc.stock)}")
public class StocksRepository extends CouchDbRepositorySupport<Item> {

    @Autowired
    public StocksRepository(CouchDbConnector db) {
        super(Item.class, db);
        initStandardDesignDocument();
    }

    public List<Item> findItem(String id) {
        return queryView("stocks", id);
    }


}
