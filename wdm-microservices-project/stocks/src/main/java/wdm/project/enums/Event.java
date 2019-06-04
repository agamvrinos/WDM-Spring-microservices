package wdm.project.enums;

public enum Event {
    SUBTRACT_STOCK("SUBTRACT_STOCK"),
    PENDING("PENDING"),
    ADD_STOCK("ADD_STOCK");

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
