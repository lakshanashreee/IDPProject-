package com.ecommerce.searchservice.exception;

public class SearchProductNotFoundException extends RuntimeException {
    public SearchProductNotFoundException(String message) { super(message); }
}
