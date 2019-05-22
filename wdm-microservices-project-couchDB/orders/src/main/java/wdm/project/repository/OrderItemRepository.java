package wdm.project.repository;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wdm.project.dto.OrderItem;

@Component
public class OrderItemRepository extends CouchDbRepositorySupport<OrderItem>{

    @Autowired
    public OrderItemRepository(CouchDbConnector db) {
        super(OrderItem.class, db);
        initStandardDesignDocument();
    }

}


