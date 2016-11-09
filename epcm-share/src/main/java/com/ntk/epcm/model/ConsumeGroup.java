package com.ntk.epcm.model;

public class ConsumeGroup {
	private int customerGroup_id;
	private String group;

	public ConsumeGroup() {
	}

	public int getCustomerGroup_id() {
		return customerGroup_id;
	}

	public void setCustomerGroup_id(int customerGroup_id) {
		this.customerGroup_id = customerGroup_id;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	@Override
	public String toString() {
		return "ConsumeGroup [customerGroup_id=" + customerGroup_id + ", group=" + group + "]";
	}
	
}
