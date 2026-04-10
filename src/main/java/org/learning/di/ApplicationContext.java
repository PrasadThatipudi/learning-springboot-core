package org.learning.di;

import org.learning.di.annotation.Component;
import org.learning.di.annotation.Primary;
import org.learning.di.annotation.Property;
import org.learning.di.error.BeanCreationError;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class ApplicationContext {
    private final HashMap<String, Object> cache = new HashMap<>();
    private final ApplicationProperties applicationProperties;

    public ApplicationContext(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    public static ApplicationContext getInstance() {
        ApplicationContext applicationContext = new ApplicationContext(ApplicationProperties.load());
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

        Object[] args = createArgumentArray(classes, parameters);

        try {
            T instance = (T) constructor.newInstance(args);
            cache.put(clz.getName(), instance);
            return instance;
        } catch (Throwable e) {
            throw new RuntimeException("Unable to instantiate class: " + clz.getName());
        }
    }

    private Object[] createArgumentArray(Collection<Class<?>> classes, Parameter[] parameters) {
        return Arrays.stream(parameters).map(parameter -> parameter.isAnnotationPresent(Property.class) ?
            applicationProperties.get(parameter.getAnnotation(Property.class).value()) :
            get(parameter.getType(), classes)
        ).toArray();
    }

    private <T> Class<?> findImplementationOf(Class<T> clz, Collection<Class<?>> classes) {

        List<Class<?>> implementedClasses = classes.stream().filter(candidate -> clz.isAssignableFrom(candidate) && !candidate.isInterface()).toList();

        if (implementedClasses.size() == 0) {
            throw BeanCreationError.noImplementationFound(clz);
        }

        return findPrimaryInstance(implementedClasses);
    }

    private Class<?> findPrimaryInstance(List<Class<?>> implementedClasses) {
        List<Class<?>> primaryClasses = implementedClasses.stream().filter(candidate -> candidate.isAnnotationPresent(Primary.class)).toList();
        if (primaryClasses.size() == 0) {
            return implementedClasses.get(0);
        }
        if (primaryClasses.size() == 1) {
            return primaryClasses.get(0);
        }

        throw BeanCreationError.multiplePrimaryBeansFound(primaryClasses);
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> clz) {
        return (T) cache.get(clz.getName());
    }
}
