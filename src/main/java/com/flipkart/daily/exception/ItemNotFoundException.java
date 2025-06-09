package com.flipkart.daily.exception;

public class ItemNotFoundException extends ApiException {
    public ItemNotFoundException(String message) {
        super(message, ErrorCode.ITEM_NOT_FOUND);
    }
}
