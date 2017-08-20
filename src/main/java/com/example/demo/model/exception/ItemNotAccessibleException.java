package com.example.demo.model.exception;

import com.example.demo.model.BaseEntity;

public class ItemNotAccessibleException extends BusinessException {
    public ItemNotAccessibleException(String s) {
        super(s);
    }

    public ItemNotAccessibleException(Class<? extends BaseEntity> entityClass, long pid, String reason) {
        super(entityClass.getName() + " ID="+pid+" is not accessible. Reason: " + reason);
    }
}