package com.ntk.epcm.manage.bean;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.ntk.epcm.model.ConsumePolicy;
import com.ntk.epcm.model.CustomerDevice;
import com.ntk.epcm.model.Device;
import com.ntk.epcm.service.ConsumePolicyService;
import com.ntk.epcm.service.CustomerDeviceService;

@Component
public class CalculatorBean implements InitializingBean {

	@Inject
	private CustomerDeviceService customerDeviceService;

	@Inject
	private ConsumePolicyService consumePolicyService;

	private Device device;

	private long payment;

	@Override
	public void afterPropertiesSet() throws Exception {

	}

	public void calculate() {
		CustomerDevice cd = customerDeviceService.findByDevice(device);
		if (cd != null) {
			List<ConsumePolicy> policies = consumePolicyService.findByConsumeGroup(cd.getConsumeGroup());
			long consumeNumber = device.getConsumeNumber();
			long oldNumber = device.getOldNumber();

			int totalConsume = (int) (consumeNumber - oldNumber);
			// check through policies to calculate final payment result;
			policies.forEach(p -> {
				int from = p.getFromConsume();
				int to = p.getToConsume();
				int price = p.getPrice();
				// when in range
				if (totalConsume >= from && totalConsume <= to) {
					// calculate consume cost, then add to payment
					int cost = (1 + totalConsume - from) * price;
					payment += cost;
				} else if (totalConsume > to) {
					// out of range! greater case, return max value
					int cost = (1 + to - from) * price;
					payment += cost;
				}
			});
		}
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
		payment = 0;
		calculate();
	}

	public long getPayment() {
		return payment;
	}

}
