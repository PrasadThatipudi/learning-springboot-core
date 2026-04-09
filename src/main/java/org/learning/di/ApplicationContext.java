package org.learning.di;

import org.learning.di.annotation.Component;
import org.learning.di.error.BeanCreationError;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.HashMap;

public class ApplicationContext {
    private final HashMap<String, Object> cache = new HashMap<>();

    public static ApplicationContext getInstance() {
        ApplicationContext applicationContext = new ApplicationContext();
        applicationContext.init();
        return applicationContext;
    }

    private void init() {
        Collection<Class<?>> allComponentClasses = ClassScanner.getAllComponentClasses();

        for (Class<?> clz : allComponentClasses) {
            get(clz, allComponentClasses);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T get(Class<T> clz, Collection<Class<?>> classes) {
        if (cache.containsKey(clz.getName())) {
            System.out.println("Returning cached instance of " + clz.getName());
            return (T) cache.get(clz.getName());
        }

        if (!clz.isAnnotationPresent(Component.class) && !clz.isInterface()) {
            throw BeanCreationError.notAComponent(clz);
        }

        return createInstanceOf(clz, classes);
    }

    @SuppressWarnings("unchecked")
    private <T> T createInstanceOf(Class<T> clz, Collection<Class<?>> classes) {
        if (clz.isInterface()) {
            Class<?> implementationClass = findImplementationOf(clz, classes);
            return (T) get(implementationClass, classes);
        }

        System.out.println("ApplicationContext: getting instance of " + clz.getName());
        Constructor<?> constructor = clz.getConstructors()[0];
        Parameter[] parameters = constructor.getParameters();

        Object[] args = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            args[i] = get(parameters[i].getType(), classes);
        }

        try {
            T instance = (T) constructor.newInstance(args);
            cache.put(clz.getName(), instance);
            return instance;
        } catch (Throwable e) {
            throw new RuntimeException("Unable to instantiate class: " + clz.getName());
        }
    }

    private <T> Class<?> findImplementationOf(Class<T> clz, Collection<Class<?>> classes) {
        for (Class<?> candidate : classes) {
            if (clz.isAssignableFrom(candidate) && !candidate.isInterface()) {
                return candidate;
            }
        }

        throw BeanCreationError.noImplementationFound(clz);
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> clz) {
        return (T) cache.get(clz.getName());
    }
}
