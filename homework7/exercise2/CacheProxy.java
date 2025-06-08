import java.io.*;
import java.lang.annotation.*;
import java.lang.reflect.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;

public class CacheProxy {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Cache {
        boolean persist() default false;
    }

    public static <T> T create(T target, Class<T> iface) {
        return (T) Proxy.newProxyInstance(
            iface.getClassLoader(),
            new Class<?>[] { iface },
            new CacheInvocationHandler<>(target)
        );
    }

    private static class CacheInvocationHandler<T> implements InvocationHandler {
        private final T target;
        private final Map<String, Object> cache = new ConcurrentHashMap<>();

        CacheInvocationHandler(T target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Cache cacheAnnotation = method.getAnnotation(Cache.class);
            if (cacheAnnotation == null) {
                return method.invoke(target, args);
            }

            String key = generateKey(method, args);
            if (cache.containsKey(key)) {
                return cache.get(key);
            }

            Object result = method.invoke(target, args);
            if (cacheAnnotation.persist()) {
                persistResult(key, result);
            }
            cache.put(key, result);
            return result;
        }

        private String generateKey(Method method, Object[] args) {
            return method.getName() + Arrays.toString(args);
        }

        private void persistResult(String key, Object result) {
            try {
                Path tempFile = Files.createTempFile("cache-", ".dat");
                try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(tempFile))) {
                    out.writeObject(result);
                }
                cache.put(key, tempFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        FibCalculator calculator = new FibCalculatorImpl();
        FibCalculator proxy = CacheProxy.create(calculator, FibCalculator.class);

        System.out.println(proxy.fib(10));
        System.out.println(proxy.fib(10)); 
    }

    public interface FibCalculator {
        @Cache(persist = true)
        long fib(int number);
    }

    public static class FibCalculatorImpl implements FibCalculator {
        @Override
        public long fib(int number) {
            if (number <= 1) {
                return number;
            }
            return fib(number - 1) + fib(number - 2);
        }
    }
}