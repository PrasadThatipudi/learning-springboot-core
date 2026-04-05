package org.learning.di;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.HashMap;

public class ApplicationContext {
    public final HashMap<String, Object> cache = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> clz) {
        if (cache.containsKey(clz.getName())) {
            System.out.println("Returning cached instance of " + clz.getName());
            return (T) cache.get(clz.getName());
        }

        System.out.println("ApplicationContext: getting instance of " + clz.getName());
        Constructor<?> constructor = clz.getConstructors()[0];
        Parameter[] parameters = constructor.getParameters();

        Object[] args = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            args[i] = get(parameters[i].getType());
            cache.put(args[i].getClass().getName(), args[i]);
        }

        try {
            T instance = (T) constructor.newInstance(args);
            cache.put(clz.getName(), instance);

            return instance;
        } catch (Throwable e) {
            throw new RuntimeException("Unable to instantiate class: " + clz.getName());
        }
    }
}
