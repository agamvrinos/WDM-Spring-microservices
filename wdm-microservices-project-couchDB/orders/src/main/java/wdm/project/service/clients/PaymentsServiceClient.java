package wdm.project.service.clients;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "payments-service")
public interface PaymentsServiceClient {

	@GetMapping("/payments/status/{order_id}")
	String getPaymentStatus(@PathVariable("order_id") String orderId);

	@PostMapping("/payments/pay/{order_id}/{user_id}/{total}")
	void payOrder(
			@PathVariable("order_id") String orderId,
			@PathVariable("user_id") String userId,
			@PathVariable("total") Integer total
	);
}
