package com.es.phoneshop.model.helping;

public enum ApplicationMessage implements MessageCode {
    DEFAULT_CODE(0),
    SUCCESS_HEAD(1),
    SUCCESS(2),
    ERROR_HEAD(3),
    NOT_NUMBER(4),
    EMPTY_FIELD(5),
    LESS_EQUAL_ZERO(6),
    NOT_ENOUGH(7),
    FRACTIONAL(8),
    NOT_FOUND(9),
    CART_UPDATE_SUCCESS(10),
    CART_ITEM_REMOVE_SUCCESS(11),
    EMPTY_FIELDS(12);

    private final Integer code;

    public static String[] bundleNames = {"default", "message.success.head", "message.success",
            "error.head", "error.not.number", "error.empty.field", "error.less.equal.zero",
            "error.not.enough", "error.fractonal", "error.not.found",
            "message.cart.update.success", "message.cart.remove.success", "error.empty.fields"
    };

    private ApplicationMessage(Integer code) {
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

}
