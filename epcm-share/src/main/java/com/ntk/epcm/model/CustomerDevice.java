package com.ntk.epcm.model;

public class CustomerDevice {
	private int customerDevice_id;
	private Customer customer;
	private Device device;
	ConsumeGroup consumeGroup;

	public int getCustomerDevice_id() {
		return customerDevice_id;
	}

	public void setCustomerDevice_id(int customerDevice_id) {
		this.customerDevice_id = customerDevice_id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public ConsumeGroup getConsumeGroup() {
		return consumeGroup;
	}

	public void setConsumeGroup(ConsumeGroup consumeGroup) {
		this.consumeGroup = consumeGroup;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + customerDevice_id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerDevice other = (CustomerDevice) obj;
		if (customerDevice_id != other.customerDevice_id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CustomerDevice [customerDevice_id=" + customerDevice_id + ", customer=" + customer + ", device="
				+ device + ", consumeGroup=" + consumeGroup + "]";
	}

}
