package com.ntk.epcm.model;

public class ConsumePolicy {
	private int consumePolicy_id;
	private ConsumeGroup group;
	private int fromConsume;
	private int toConsume;
	private int price;

	public int getConsumePolicy_id() {
		return consumePolicy_id;
	}

	public void setConsumePolicy_id(int consumePolicy_id) {
		this.consumePolicy_id = consumePolicy_id;
	}

	public int getFromConsume() {
		return fromConsume;
	}

	public void setFromConsume(int fromConsume) {
		this.fromConsume = fromConsume;
	}

	public int getToConsume() {
		return toConsume;
	}

	public void setToConsume(int toConsume) {
		this.toConsume = toConsume;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + consumePolicy_id;
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
		ConsumePolicy other = (ConsumePolicy) obj;
		if (consumePolicy_id != other.consumePolicy_id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ConsumePolicy [consumePolicy_id=" + consumePolicy_id + ", fromConsume=" + fromConsume + ", toConsume="
				+ toConsume + ", price=" + price + "]";
	}

	public ConsumeGroup getGroup() {
		return group;
	}

	public void setGroup(ConsumeGroup group) {
		this.group = group;
	}

}
