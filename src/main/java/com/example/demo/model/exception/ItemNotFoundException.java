package com.example.demo.model.exception;

import com.example.demo.model.BaseEntity;

public class ItemNotFoundException extends BusinessException {

    public ItemNotFoundException(String s) {
        super(s);
    }


    public ItemNotFoundException(Class<? extends BaseEntity> entityClass, long pid) {
        this(entityClass.getName() + " ID="+pid);
    }
}