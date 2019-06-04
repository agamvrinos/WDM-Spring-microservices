package wdm.project.enums;

public enum Status {
    SUCCESS("SUCCESS"),
    FAILURE("FAILURE");

    private String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Status findStatusEnum(String status) {
        for (Status each : Status.values()) {
            if (each.value.equals(status)) {
                return each;
            }
        }
        return null;
    }
}
