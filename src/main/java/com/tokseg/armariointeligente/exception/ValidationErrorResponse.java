package com.tokseg.armariointeligente.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ValidationErrorResponse extends ErrorResponse {
    private Map<String, String> errors;

    public ValidationErrorResponse(Date timestamp, int status, String error, String message, String path) {
        super(timestamp, status, error, message, path);
        this.errors = new HashMap<>();
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public void addError(String field, String message) {
        this.errors.put(field, message);
    }
}
