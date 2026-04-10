package org.learning.di.error;

import java.util.List;

public class BeanCreationError extends RuntimeException {
    public BeanCreationError(String message) {
        super(message);
    }

    public static <T> BeanCreationError notAComponent(Class<T> clz) {
        String message = clz.getName() + " is not annotated with @Component";

        return new BeanCreationError(message);
    }

    public static <T> BeanCreationError noImplementationFound(Class<T> clz) {
        String message = "No implementation found for " + clz.getName();
        return new BeanCreationError(message);
    }

    public static BeanCreationError multiplePrimaryBeansFound(List<Class<?>> primaryClasses) {
        String message = "Multiple primary beans found: " + primaryClasses;
        return new BeanCreationError(message);
    }
}
