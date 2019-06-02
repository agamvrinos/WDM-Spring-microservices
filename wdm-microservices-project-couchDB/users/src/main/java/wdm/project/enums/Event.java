package wdm.project.enums;

public enum Event {
	REDUCE_CREDIT("REDUCE_CREDIT");

	private String value;

	Event(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public static Event findEventEnum(String event) {
		for (Event each : Event.values()) {
			if (each.value.equals(event)) {
				return each;
			}
		}
		return null;
	}
}