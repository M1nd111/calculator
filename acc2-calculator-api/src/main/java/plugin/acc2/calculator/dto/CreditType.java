package plugin.acc2.calculator.dto;

import lombok.Getter;

@Getter
public enum CreditType {
    MORTGAGE(1L),
    CAR_CREDIT(2L),
    CONSUMER_CREDIT(3L);

    private final long id;

    CreditType(long id) {
        this.id = id;
    }

    public static CreditType fromId(long id) {
        for (CreditType type : CreditType.values()) {
            if (type.id == id) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown CreditType id: " + id);
    }
}
