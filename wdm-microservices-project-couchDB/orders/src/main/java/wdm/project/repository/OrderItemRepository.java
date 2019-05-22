package wdm.project.repository;

import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wdm.project.dto.OrderItem;

import java.util.List;

@Component
public class OrderItemRepository extends CouchDbRepositorySupport<OrderItem>{

    @Autowired
    public OrderItemRepository(CouchDbConnector db) {
        super(OrderItem.class, db);
        initStandardDesignDocument();
    }

    @GenerateView
    public List<OrderItem> findByOrderId(String orderId) {
        return queryView("by_orderId", orderId);
    }

}


