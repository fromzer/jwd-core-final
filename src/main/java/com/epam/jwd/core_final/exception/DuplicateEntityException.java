package com.epam.jwd.core_final.exception;

public class DuplicateEntityException extends Exception {
    private final String entityName;

    public DuplicateEntityException(String entityName) {
        super();
        this.entityName = entityName;
    }

    @Override
    public String getMessage() {
        return "Duplicate entity name: "
                + entityName;
    }
}
