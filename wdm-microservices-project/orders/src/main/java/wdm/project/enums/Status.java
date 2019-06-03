package wdm.project.enums;

public enum Status {
    SUCCESS("SUCCESS"),
    STOCK_PENDING("STOCK_PENDING"),
    STOCK_SUCCESS("STOCK_SUCCESS"),
    STOCK_FAILURE("STOCK_FAILURE"),
    PAYMENT_FAILURE("PAYMENT_FAILURE"),
    ROLLBACK_PENDING("ROLLBACK_PENDING");

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
