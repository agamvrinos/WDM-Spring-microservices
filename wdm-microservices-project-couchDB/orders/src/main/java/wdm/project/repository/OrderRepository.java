package wdm.project.repository;

import org.ektorp.*;
import org.ektorp.support.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import wdm.project.dto.Order;

@Component
public class OrderRepository extends CouchDbRepositorySupport<Order> {

    @Autowired
    public OrderRepository(CouchDbConnector db) {
        super(Order.class, db);
        initStandardDesignDocument();
    }

}