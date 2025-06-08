import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

public class RandomObjectGenerator {

    public <T> T nextObject(Class<T> clazz, String factoryMethodName) throws Exception {
        try {
            Method factoryMethod = clazz.getMethod(factoryMethodName);
            return (T) factoryMethod.invoke(null);
        } catch (NoSuchMethodException e) {
            Constructor<T> constructor = findConstructor(clazz);
            return constructor != null ? createObjectFromConstructor(constructor) : null;
        }
    }

    private <T> T createObjectFromConstructor(Constructor<T> constructor) throws Exception {
        Parameter[] parameters = constructor.getParameters();
        Object[] arguments = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            arguments[i] = generateRandomValue(parameters[i]);
        }

        return constructor.newInstance(arguments);
    }

    private <T> Constructor<T> findConstructor(Class<T> clazz) {
        for (Constructor<?> constructor : clazz.getConstructors()) {
            if (constructor.getParameterCount() > 0) {
                return (Constructor<T>) constructor;
            }
        }
        return null;
    }

    private Object generateRandomValue(Parameter parameter) {
        Class<?> type = parameter.getType();

        if (parameter.isAnnotationPresent(NotNull.class)) {
            if (type.equals(String.class)) {
                return "RandomString";
            } else if (type.equals(int.class)) {
                return 42;
            } else if (type.equals(double.class)) {
                return 3.14;
            } else if (type.equals(boolean.class)) {
                return true;
            }
        }

        if (parameter.isAnnotationPresent(Min.class) || parameter.isAnnotationPresent(Max.class)) {
            return handleMinMaxAnnotations(parameter);
        }

        if (type.equals(String.class)) {
            return "RandomString";
        } else if (type.equals(int.class)) {
            return new Random().nextInt(100);
        } else if (type.equals(double.class)) {
            return new Random().nextDouble() * 100;
        } else if (type.equals(boolean.class)) {
            return new Random().nextBoolean();
        }

        return null;
    }

    private Object handleMinMaxAnnotations(Parameter parameter) {
        Min minAnnotation = parameter.getAnnotation(Min.class);
        Max maxAnnotation = parameter.getAnnotation(Max.class);

        int min = minAnnotation != null ? minAnnotation.value() : 0;
        int max = maxAnnotation != null ? maxAnnotation.value() : 100;

        return new Random().nextInt(max - min + 1) + min;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.PARAMETER)
    public @interface NotNull {}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.PARAMETER)
    public @interface Min {
        int value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.PARAMETER)
    public @interface Max {
        int value();
    }
}
