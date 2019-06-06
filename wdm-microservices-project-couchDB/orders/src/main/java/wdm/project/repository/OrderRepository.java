package wdm.project.repository;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wdm.project.dto.Order;

@Component
@View(name = "orders", map = "function(doc) { if (doc.type == 'object' ) emit(doc._id, doc.userId)}")
public class OrderRepository extends CouchDbRepositorySupport<Order> {

    @Autowired
    public OrderRepository(CouchDbConnector db) {
        super(Order.class, db);
        initStandardDesignDocument();
    }

    public List<Order> findOrder(String id) {
        return queryView("orders", id);
    }

}
