package wdm.project.service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "payments-service")
public interface PaymentsServiceClient {

	@GetMapping("/payments/status/{order_id}")
	String getPaymentStatus(@PathVariable("order_id") Long orderId);

	@PostMapping("/payments/pay/{order_id}/{user_id}/{total}")
	void payOrder(
			@PathVariable("order_id") Long orderId,
			@PathVariable("user_id") Long userId,
			@PathVariable("total") Integer total
	);
}
