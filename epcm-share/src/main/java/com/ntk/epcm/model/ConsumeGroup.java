package com.ntk.epcm.model;

public class ConsumeGroup {
	private int consumeGroup_id;
	private String group;

	public ConsumeGroup() {
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	@Override
	public String toString() {
		return "ConsumeGroup [customerGroup_id=" + consumeGroup_id + ", group=" + group + "]";
	}

	public int getConsumeGroup_id() {
		return consumeGroup_id;
	}

	public void setConsumeGroup_id(int consumeGroup_id) {
		this.consumeGroup_id = consumeGroup_id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + consumeGroup_id;
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
		ConsumeGroup other = (ConsumeGroup) obj;
		if (consumeGroup_id != other.consumeGroup_id)
			return false;
		return true;
	}

}
