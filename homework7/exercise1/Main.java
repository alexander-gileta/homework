import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.Random;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@interface NotNull {}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@interface Min {
    int value();
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@interface Max {
    int value();
}

class RandomObjectGenerator {
    private final Random random = new Random();

    public <T> T nextObject(Class<T> clazz) {
        return nextObject(clazz, null);
    }

    public <T> T nextObject(Class<T> clazz, String factoryMethodName) {
        try {
            if (factoryMethodName != null) {
                for (Method method : clazz.getDeclaredMethods()) {
                    if (Modifier.isStatic(method.getModifiers()) &&
                        method.getName().equals(factoryMethodName)) {
                        method.setAccessible(true);
                        Object[] args = generateArguments(method);
                        return clazz.cast(method.invoke(null, args));
                    }
                }
                throw new IllegalArgumentException("No factory method found: " + factoryMethodName);
            }

            if (clazz.isRecord()) {
                Constructor<?> constructor = clazz.getDeclaredConstructors()[0];
                constructor.setAccessible(true);
                Object[] args = generateArguments(constructor);
                return clazz.cast(constructor.newInstance(args));
            }

            Constructor<T> ctor = clazz.getDeclaredConstructor();
            ctor.setAccessible(true);
            T instance = ctor.newInstance();
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                field.set(instance, generateValue(field.getType()));
            }
            return instance;

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate object", e);
        }
    }

    private Object[] generateArguments(Executable executable) {
        Parameter[] parameters = executable.getParameters();
        Object[] args = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            Parameter param = parameters[i];
            args[i] = generateValueWithAnnotations(param.getType(), param.getAnnotations());
        }

        return args;
    }

    private Object generateValueWithAnnotations(Class<?> type, Annotation[] annotations) {
        Integer min = null;
        Integer max = null;
        boolean notNull = false;

        for (Annotation annotation : annotations) {
            if (annotation instanceof NotNull) notNull = true;
            if (annotation instanceof Min) min = ((Min) annotation).value();
            if (annotation instanceof Max) max = ((Max) annotation).value();
        }

        if (!notNull && random.nextBoolean()) {
            return null;
        }

        if (type == int.class || type == Integer.class) {
            int lo = (min != null) ? min : 0;
            int hi = (max != null) ? max : 100;
            return lo + random.nextInt(hi - lo + 1);
        }

        if (type == String.class) {
            return "str" + random.nextInt(1000);
        }

        return null;
    }

    private Object generateValue(Class<?> type) {
        return generateValueWithAnnotations(type, new Annotation[0]);
    }
}

record MyRecord(@NotNull String name, @Min(10) @Max(20) int age) {}

class MyClass {
    private final String name;
    private final int age;

    private MyClass(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public static MyClass create(@NotNull String name, @Min(18) @Max(30) int age) {
        return new MyClass(name, age);
    }

    @Override
    public String toString() {
        return "MyClass{name='" + name + "', age=" + age + "}";
    }
}

public class Main {
    public static void main(String[] args) {
        RandomObjectGenerator rog = new RandomObjectGenerator();

        MyRecord rec = rog.nextObject(MyRecord.class);
        System.out.println("Generated record: " + rec);

        MyClass obj = rog.nextObject(MyClass.class, "create");
        System.out.println("Generated class:  " + obj);
    }
}
