package wdm.project.repository;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wdm.project.dto.Order;

@Component
public class OrderRepository extends CouchDbRepositorySupport<Order> {

    @Autowired
    public OrderRepository(CouchDbConnector db) {
        super(Order.class, db);
        initStandardDesignDocument();
    }

}
