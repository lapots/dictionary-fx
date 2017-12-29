package com.lapots.breed.dictionary.domain.support;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.lang.reflect.Field;

public class NameBasedGenerator implements IdentifierGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        String postfix = "_ID";
        try {
            Field field = object.getClass().getDeclaredField("name");
            field.setAccessible(true);
            String value = (String) field.get(object);
            return value.toUpperCase().concat(postfix);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
