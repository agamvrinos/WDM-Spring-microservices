package wdm.project.repository;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.GenerateView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wdm.project.dto.Payment;

import java.util.List;

@Component
public class PaymentsRepository extends CouchDbRepositorySupport<Payment> {

	@Autowired
	public PaymentsRepository(CouchDbConnector db) {
		super(Payment.class, db);
		initStandardDesignDocument();
	}

	@GenerateView
	public List<Payment> findByOrderId(String orderId) {
		return queryView("by_orderId", orderId);
	}
}
