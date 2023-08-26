package com.rminaya.clothes.store.clothes_store.util.exceptions;

public class IdNotFoundException extends RuntimeException {
    private static final String ERROR_MESSAGE = "El registro no existe en %s";
    public IdNotFoundException(String tableName){
        super(String.format(ERROR_MESSAGE, tableName));
    }
}
